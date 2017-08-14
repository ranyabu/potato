package org.mengdadou.rpc.exchange;

/**
 * Created by mengdadou on 17-5-16.
 */
public enum ResponseCode {
    SUCCESS((byte) 1),
    ERROR((byte) 2),
    EXCEPTION((byte) 3),;
    
    private byte value;
    
    ResponseCode(byte value) {
        this.value = value;
    }
    
    public byte getValue() {
        return value;
    }
    
}
