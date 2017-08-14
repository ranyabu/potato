package org.mengdadou.net.msg;

/**
 * Created by mengdadou on 16-12-22.
 */
public enum MsgType {
    BEAT((byte) -1),
    BIZ((byte) 1),;

    private byte type;

    MsgType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }
}
