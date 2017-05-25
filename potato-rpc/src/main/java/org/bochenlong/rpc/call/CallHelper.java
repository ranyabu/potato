package org.bochenlong.rpc.call;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.bochenlong.net.NettyHelper;
import org.bochenlong.net.client.NettyClient;
import org.bochenlong.net.common.exception.RemoteException;
import org.bochenlong.rpc.RpcManager;
import org.bochenlong.rpc.exchange.Request;
import org.bochenlong.rpc.exchange.Response;
import org.bochenlong.rpc.exchange.assist.RequestType;
import org.bochenlong.rpc.exchange.assist.RpcFuture;
import org.bochenlong.rpc.func.URLHandlerUtil;

import java.util.concurrent.*;

/**
 * Created by bochenlong on 17-3-27.
 */
public class CallHelper {
    protected static ConcurrentHashMap<String, Channel> CHANNELS
            = new ConcurrentHashMap<>();
    
    protected static ConcurrentHashMap<Long, RpcFuture<Response>> FUTURES
            = new ConcurrentHashMap<>();
    
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
        Channel channel = new NettyClient(URLHandlerUtil.getHost(restURL)).channel();
        String hostKey = URLHandlerUtil.getHostKey(restURL);
        channel.attr(ATTR_CHANNEL_KEY).setIfAbsent(hostKey);
        CHANNELS.put(URLHandlerUtil.getHostKey(restURL), channel);
        return channel;
    }
    
    public static void writeResp(Response response) {
        RpcFuture<Response> f = FUTURES.get(response.getId());
        if (f == null) return;
        f.set(response);
        FUTURES.remove(response.getId());
    }
}
