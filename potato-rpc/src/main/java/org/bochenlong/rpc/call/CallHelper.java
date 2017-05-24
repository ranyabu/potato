package org.bochenlong.rpc.call;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.AttributeKey;
import org.bochenlong.net.NettyHelper;
import org.bochenlong.net.NettyManager;
import org.bochenlong.net.client.NettyClient;
import org.bochenlong.net.common.exception.RemoteException;
import org.bochenlong.net.msg.MsgFactory;
import org.bochenlong.net.util.SpiUtil;
import org.bochenlong.rpc.RpcManager;
import org.bochenlong.rpc.exchange.Request;
import org.bochenlong.rpc.exchange.Response;
import org.bochenlong.rpc.exchange.assist.RequestType;
import org.bochenlong.rpc.exchange.assist.RpcFuture;
import org.bochenlong.rpc.func.URLHandler;

import java.util.concurrent.*;

/**
 * Created by bochenlong on 17-3-27.
 */
public class CallHelper {
    protected static ConcurrentHashMap<String, Channel> CHANNELS
            = new ConcurrentHashMap<>();
    
    protected static ConcurrentHashMap<Long, RpcFuture<Response>> FUTURES
            = new ConcurrentHashMap<>();
    private static URLHandler potatoURLHandler = SpiUtil.getServiceImpl(URLHandler.class);
    
    
    public static final AttributeKey<String> ATTR_CHANNEL_KEY =
            AttributeKey.valueOf("CHANNEL_KEY");
    
    public static Response sync(String restURL, Object... data) throws RemoteException, InterruptedException, ExecutionException, TimeoutException {
        return async(restURL, data).get(RpcManager.getExecuteTimeOut(), TimeUnit.MINUTES);
    }
    
    public static Future<Response> async(String restURL, Object... data) throws RemoteException {
        Request request = new Request(RequestType.SYNC.getType(), restURL, data);
        NettyHelper.send(connect(restURL), request);
        return FUTURES.computeIfAbsent(request.getId(), a -> new RpcFuture<>());
    }
    
    private static void notify(String restURL, Object... data) throws RemoteException {
        Request request = new Request(RequestType.NOTIFY.getType(), restURL, data);
        NettyHelper.send(connect(restURL), request);
    }
    
    private static Channel connect(String restURL) {
        return CHANNELS.getOrDefault(potatoURLHandler.getHostKey(restURL), syncConnect(restURL));
    }
    
    private synchronized static Channel syncConnect(String restURL) {
        if (CHANNELS.containsKey(potatoURLHandler.getHostKey(restURL)))
            return CHANNELS.get(potatoURLHandler.getHostKey(restURL));
        return connR(restURL);
    }
    
    // not thread safe
    // means that will create N conn to one remote
    // remote can be any content
    // but you should impl the URLHandler for get the host
    private static Channel connR(String restURL) {
        Channel channel = new NettyClient(potatoURLHandler.getHost(restURL)).channel();
        String hostKey = potatoURLHandler.getHostKey(restURL);
        channel.attr(ATTR_CHANNEL_KEY).setIfAbsent(hostKey);
        CHANNELS.put(potatoURLHandler.getHostKey(restURL), channel);
        return channel;
    }
    
    public static void writeResp(Response response) {
        RpcFuture<Response> f = FUTURES.get(response.getId());
        if (f == null) return;
        f.set(response);
        FUTURES.remove(response.getId());
    }
}
