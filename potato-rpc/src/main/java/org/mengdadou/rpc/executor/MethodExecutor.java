package org.mengdadou.rpc.executor;

import org.mengdadou.rpc.exchange.Response;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by mengdadou on 17-3-24.
 */
public class MethodExecutor {
    private ExecutorService service;

    public MethodExecutor(int pool) {
        service = Executors.newFixedThreadPool(pool);
    }

    public void execute(Runnable runnable) {
        service.execute(runnable);
    }

    public Future<Response> submit(Callable<Response> callable) {
        return service.submit(callable);
    }
}
