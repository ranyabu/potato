package org.bochenlong.rpc.call;

import org.bochenlong.rpc.exchange.assist.RpcFuture;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by bochenlong on 16-11-14.
 */
public class FutureMapCleaner {
    public static void clean() {
        Runnable runnable = () ->
                CallHelper.FUTURES.entrySet()
                        .stream()
                        .filter(a -> a.getValue().getStatus() != RpcFuture.RUNNING)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList())
                        .stream()
                        .forEach(CallHelper.FUTURES::remove);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(runnable, 5, 60, TimeUnit.SECONDS);
    }
}
