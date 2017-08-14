package org.mengdadou.rpc.exchange;

/**
 * Created by mengdadou on 17-3-27.
 */
public class Response {
    private long id;
    private byte code = 1;
    private Object data;
    
    
    public Response(long id, Object data) {
        this.id = id;
        this.data = data;
    }
    
    public Response(long id, byte code, Object data) {
        this.id = id;
        this.code = code;
        this.data = data;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public byte getCode() {
        return code;
    }
    
    public void setCode(byte code) {
        this.code = code;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
}
