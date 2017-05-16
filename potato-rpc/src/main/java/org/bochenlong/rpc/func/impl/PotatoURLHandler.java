package org.bochenlong.rpc.func.impl;

import org.bochenlong.rpc.func.URLHandler;

/**
 * Created by bochenlong on 17-3-27.
 * URL :  potato://127.0.0.1/chKey/path
 */
public class PotatoURLHandler implements URLHandler {
    @Override
    public String getHost(String resultURL) {
        String replace = resultURL.replace("potato://", "");
        return replace.substring(0, replace.indexOf("/"));
    }
    
    @Override
    public String getPath(String resultURL) {
        int index;
        for (int i = 0; i < 4; i++) {
            index = resultURL.indexOf("/");
            resultURL = resultURL.substring(index + 1);
        }
        return "/" + resultURL;
    }
    
    @Override
    public String getHostKey(String resultURL) {
        String replace = resultURL.replace("potato://", "");
        return replace.substring(0, replace.indexOf("/"));
    }
    
}
