package org.mengdadou.rpc;

import org.mengdadou.rpc.call.CallHelper;

/**
 * Created by mengdadou on 17-8-21.
 */
public class PotatoCtxBean {
    private String restURL;
    private String remote;
    
    public PotatoCtxBean(String restURL, String remote) {
        this.restURL = restURL;
        this.remote = remote;
    }
    
    public void reset() {
        CallHelper.reset(restURL);
    }
    
    public String getRemote() {
        return remote;
    }
}
