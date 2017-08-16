package org.mengdadou.rpc;

import io.netty.channel.ChannelHandlerContext;
import org.mengdadou.net.func.IDataHandle;
import org.mengdadou.rpc.call.CallHelper;
import org.mengdadou.rpc.exchange.Request;
import org.mengdadou.rpc.exchange.Response;
import org.mengdadou.rpc.exchange.ResponseUtil;
import org.mengdadou.rpc.executor.MethodInvoker;
import org.mengdadou.rpc.scan.ScanPotatoRpc;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mengdadou on 17-3-27.
 */
public class RpcDispatcher implements IDataHandle {
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
        Request       request = (Request) data;
        MethodInvoker invoker = potatoRpcMap.get(request.getPath());
        
        if (invoker == null) {
            CallHelper.response(ctx, ResponseUtil.exception(request.getId(),
                    "no service find for this path"));
            return;
        }
        invoker.invoke(request, ctx);
    }
}
