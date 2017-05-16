package org.bochenlong.rpc.exchange.assist;

/**
 * Created by bochenlong on 17-3-27.
 */
public enum RequestType {
    ASYNC((byte) 1),    // 异步请求,会收到回复
    SYNC((byte) 2),     // 同步请求,会收到回复
    NOTIFY((byte) 3);   // 异步通知,相当于UDP

    private byte type;

    RequestType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return this.type;
    }
}
