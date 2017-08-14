package org.mengdadou.rpc.executor;

import io.netty.channel.ChannelHandlerContext;
import org.mengdadou.rpc.RpcManager;
import org.mengdadou.rpc.call.CallHelper;
import org.mengdadou.rpc.exchange.Request;
import org.mengdadou.rpc.exchange.Response;
import org.mengdadou.rpc.exchange.ResponseUtil;
import org.mengdadou.rpc.exchange.assist.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * Created by mengdadou on 17-3-24.
 */
public class MethodInvoker {
    private static Logger logger = LoggerFactory.getLogger(MethodInvoker.class);
    
    private Object instance;
    private Method method;
    private MethodExecutor executor;
    
    public MethodInvoker(Object instance, Method method, MethodExecutor executor) {
        this.instance = instance;
        this.method = method;
        this.executor = executor;
    }
    
    public void invoke(Request request, ChannelHandlerContext ctx) {
        byte type = request.getType();
        if (type == RequestType.NOTIFY.getType()) {
            executor.execute(noResponse.apply(request));
        } else {
            // else type is async sync
            try {
                Future<Response> future = executor.submit(needResponse.apply(request));
                CallHelper.response(ctx, future.get(RpcManager.singleton().getEXECUTE_TIME_OUT(), TimeUnit.MILLISECONDS));
            } catch (InterruptedException | ExecutionException | TimeoutException future) {
                logger.error("future get exception: {}", future.getMessage());
                future.printStackTrace();
                CallHelper.response(ctx, ResponseUtil.exception(request.getId(), String.format("EXCEPTION : %s", future.getMessage())));
            }
        }
    }
    
    private Function<Request, Runnable> noResponse = request ->
            () -> {
                try {
                    method.invoke(instance, request.getArgs());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    logger.error("method invoke exception: {}", e.getMessage());
                    e.printStackTrace();
                }
            };
    
    private Function<Request, Callable<Response>> needResponse = request ->
            () -> {
                try {
                    Object result = method.invoke(instance, request.getArgs());
                    return ResponseUtil.success(request.getId(), result);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    logger.error("method invoke exception: {}", e.getMessage());
                    e.printStackTrace();
                    return ResponseUtil.exception(request.getId(), e.getMessage());
                }
            };
    
    public Object getInstance() {
        return instance;
    }
    
    public void setInstance(Object instance) {
        this.instance = instance;
    }
    
    public Method getMethod() {
        return method;
    }
    
    public void setMethod(Method method) {
        this.method = method;
    }
}