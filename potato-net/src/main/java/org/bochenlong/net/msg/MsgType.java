package org.bochenlong.net.msg;

/**
 * Created by bochenlong on 16-12-22.
 */
public enum MsgType {
    HEART((byte) -1),
    BIZ((byte) 1),;

    private byte type;

    MsgType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }
}
