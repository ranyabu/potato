package org.mengdadou.rpc.func.impl;

import org.mengdadou.rpc.func.URLHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Created by mengdadou on 17-3-27.
 * URL :  potato://127.0.0.1/chKey/path
 */
public class PotatoURLHandler implements URLHandler {
    private ConcurrentHashMap<String, String> hostMapping = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> pathMapping = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> hostKeyMapping = new ConcurrentHashMap<>();
    
    @Override
    public String getHost(String resultURL) {
        return hostMapping.computeIfAbsent(resultURL, a -> hostFunc.apply(a));
    }
    
    private Function<String, String> hostFunc = resultURL -> {
        String replace = resultURL.replace("potato://", "");
        return replace.substring(0, replace.indexOf("/"));
    };
    
    @Override
    public String getPath(String resultURL) {
        return pathMapping.computeIfAbsent(resultURL, a -> pathFunc.apply(a));
    }
    
    private Function<String, String> pathFunc = resultURL ->
            resultURL.substring(getStringIndex(resultURL, "/", 4));
    
    
    @Override
    public String getHostKey(String resultURL) {
        return hostKeyMapping.computeIfAbsent(resultURL, a -> hostKeyFunc.apply(a));
    }
    
    private Function<String, String> hostKeyFunc = resultURL ->
            resultURL.substring(getStringIndex(resultURL, "/", 2), getStringIndex(resultURL, "/", 4));
    
    
    private int getStringIndex(String origin, @SuppressWarnings("SameParameterValue") String str, int n) {
        int index = 0;
        int current = 0;
        for (int i = 0; i < n; i++) {
            index = origin.indexOf(str, current);
            if (index == -1) return -1;
            current = str.length() + index;
        }
        return index;
    }
    
}
