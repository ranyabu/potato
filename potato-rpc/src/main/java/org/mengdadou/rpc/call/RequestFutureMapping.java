package org.mengdadou.rpc.call;

import org.mengdadou.rpc.exchange.assist.PotatoFuture;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mengdadou on 17-8-16.
 */
public class RequestFutureMapping {
    private static class Holder {
        private static RequestFutureMapping mapping = new RequestFutureMapping();
    }
    
    public static RequestFutureMapping singleton() {
        return RequestFutureMapping.Holder.mapping;
    }
    
    private ConcurrentHashMap<Long, PotatoFuture> FUTURES
            = new ConcurrentHashMap<>();
    
    public PotatoFuture add(long requestId, PotatoFuture future) {
        return FUTURES.computeIfAbsent(requestId, a -> future);
    }
    
    public PotatoFuture get(long requestId) {
        return FUTURES.get(requestId);
    }
    
    public void remove(long requestId) {
        FUTURES.remove(requestId);
    }
}
