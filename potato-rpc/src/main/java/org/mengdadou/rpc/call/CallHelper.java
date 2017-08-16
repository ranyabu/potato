package org.mengdadou.rpc.call;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.mengdadou.net.NettyHelper;
import org.mengdadou.net.exception.RemoteException;
import org.mengdadou.rpc.RpcManager;
import org.mengdadou.rpc.exchange.Request;
import org.mengdadou.rpc.exchange.Response;
import org.mengdadou.rpc.exchange.assist.PotatoFuture;
import org.mengdadou.rpc.exchange.assist.RequestType;
import org.mengdadou.rpc.func.URLHandlerUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by mengdadou on 17-3-27.
 */
public class CallHelper {
    protected static ConcurrentHashMap<String, Channel> CHANNELS
            = new ConcurrentHashMap<>();
    
    
    public static final AttributeKey<String> ATTR_CHANNEL_KEY =
            AttributeKey.valueOf("CHANNEL_KEY");
    
    public static void response(ChannelHandlerContext ctx, Response response) {
        try {
            NettyHelper.send(ctx, response);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    public static Response sync(String restURL, Object... data) throws RemoteException, InterruptedException, ExecutionException, TimeoutException {
        return async(restURL, data).get(RpcManager.singleton().getEXECUTE_TIME_OUT(), TimeUnit.MINUTES);
    }
    
    public static PotatoFuture async(String restURL, Object... data) throws RemoteException {
        Request request = new Request(RequestType.SYNC.getType(), restURL, data);
        NettyHelper.send(connect(restURL), request);
        return RequestFutureMapping.singleton().add(request.getId(), new PotatoFuture(request.getId()));
    }
    
    private static void notify(String restURL, Object... data) throws RemoteException {
        Request request = new Request(RequestType.NOTIFY.getType(), restURL, data);
        NettyHelper.send(connect(restURL), request);
    }
    
    private static Channel connect(String restURL) {
        return CHANNELS.getOrDefault(URLHandlerUtil.getHostKey(restURL), syncConnect(restURL));
    }
    
    private synchronized static Channel syncConnect(String restURL) {
        if (CHANNELS.containsKey(URLHandlerUtil.getHostKey(restURL)))
            return CHANNELS.get(URLHandlerUtil.getHostKey(restURL));
        return connR(restURL);
    }
    
    // not thread safe
    // means that will create N conn to one remote
    // remote can be any content
    // but you should impl the URLHandler for get the host
    private static Channel connR(String restURL) {
        Channel channel = NettyHelper.connH(URLHandlerUtil.getHost(restURL));
        String  hostKey = URLHandlerUtil.getHostKey(restURL);
        channel.attr(ATTR_CHANNEL_KEY).setIfAbsent(hostKey);
        CHANNELS.put(URLHandlerUtil.getHostKey(restURL), channel);
        return channel;
    }
    
    public static void writeResp(Response response) {
        PotatoFuture f = RequestFutureMapping.singleton().get(response.getId());
        if (f == null) return;
        f.set(response);
        RequestFutureMapping.singleton().remove(response.getId());
    }
}
