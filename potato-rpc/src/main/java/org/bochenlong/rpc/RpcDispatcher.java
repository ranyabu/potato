package org.bochenlong.rpc;

import io.netty.channel.ChannelHandlerContext;
import org.bochenlong.net.func.DataHandler;
import org.bochenlong.rpc.call.CallHelper;
import org.bochenlong.rpc.exchange.Request;
import org.bochenlong.rpc.exchange.Response;
import org.bochenlong.rpc.executor.MethodInvoker;
import org.bochenlong.rpc.scan.ScanPotatoRpc;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bochenlong on 17-3-27.
 */
public class RpcDispatcher implements DataHandler {
    private ConcurrentHashMap<String, MethodInvoker> potatoRpcMap;
    
    public RpcDispatcher() {
        this.potatoRpcMap = new ScanPotatoRpc().findAllPotatoRpc("org.bochenlong");
    }
    
    @Override
    public void execute(ChannelHandlerContext ctx, Object data) {
        if (data instanceof Response) {
            CallHelper.writeResp((Response) data);
            return;
        }
        Request request = (Request) data;
        potatoRpcMap.get(request.getPath())
                .invoke(request, ctx);
    }
}
