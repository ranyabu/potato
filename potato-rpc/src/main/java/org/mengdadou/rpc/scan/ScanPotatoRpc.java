package org.mengdadou.rpc.scan;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.mengdadou.rpc.annotation.Potato;
import org.mengdadou.rpc.annotation.PotatoRpc;
import org.mengdadou.rpc.executor.MethodExecutor;
import org.mengdadou.rpc.executor.MethodInvoker;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by mengdadou on 17-3-23.
 */
public class ScanPotatoRpc {

    public ConcurrentHashMap<String, MethodInvoker> findAllPotatoRpc(String packageName) {
        ConcurrentHashMap<String, MethodInvoker> potatoRpcMap =
                new ConcurrentHashMap<>();
        FastClasspathScanner scanner = new FastClasspathScanner(packageName);
        scanner.matchClassesWithAnnotation(Potato.class, aClass -> this.serviceInit(aClass, potatoRpcMap)).scan(1);
        return potatoRpcMap;
    }

    // single thread make safe
    private void serviceInit(Class<?> clazz, ConcurrentHashMap<String, MethodInvoker> potatoRpcMap) {
        Potato potato = clazz.getAnnotation(Potato.class);
        MethodExecutor executor = null;

        for (Method a : clazz.getDeclaredMethods()) {
            PotatoRpc potatoRpc = a.getAnnotation(PotatoRpc.class);
            if (potatoRpc == null) continue;

            String path = potatoRpc.path();

            if (potatoRpcMap.containsKey(path)) {
                MethodInvoker has = potatoRpcMap.get(path);
                throw new RuntimeException(
                        String.format("%s - %s  should not has the same path %s - %s",
                                clazz.getSimpleName(), a.getName(),
                                has.getInstance().getClass().getSimpleName(), has.getMethod().getName())
                );
            } else {
                if (executor == null) executor = new MethodExecutor(potato.pool());
                try {
                    potatoRpcMap.put(path, new MethodInvoker(clazz.newInstance(), a, executor));
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(
                            String.format("%s must contain a no arg constructor",
                                    clazz.getSimpleName()));
                }
            }
        }
    }


}
