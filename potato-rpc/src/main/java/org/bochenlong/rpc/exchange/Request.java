package org.bochenlong.rpc.exchange;

import org.bochenlong.rpc.func.URLHandlerUtil;
import org.bochenlong.rpc.idgenerator.IdWorker;

/**
 * Created by bochenlong on 17-3-27.
 */
public class Request {
    private byte version = 0x01;
    
    private long id = IdWorker.instance().nextId();
    
    private byte type;
    
    private String URL;
    private String path;
    
    private Object[] args;
    
    public Request(byte type, String URL, Object... args) {
        this.type = type;
        this.URL = URL;
        this.args = args;
        this.path = URLHandlerUtil.getPath(URL);
    }
    
    
    public long getId() {
        return id;
    }
    
    public byte getVersion() {
        return version;
    }
    
    public byte getType() {
        return type;
    }
    
    public void setType(byte type) {
        this.type = type;
    }
    
    public String getURL() {
        return URL;
    }
    
    public void setURL(String URL) {
        this.URL = URL;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public Object[] getArgs() {
        return args;
    }
    
    public void setArgs(Object[] args) {
        this.args = args;
    }
}
