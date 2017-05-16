package org.bochenlong.net.msg.bean;

import org.bochenlong.net.msg.MsgType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bochenlong on 16-11-4.
 */
public class Header {
    private byte version = 0x01;
    private int length;
    /**
     * see {@link MsgType}
     */
    private byte type;
    private Map<String, Object> systemic = new HashMap<>();

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Header{" +
                "version=" + version +
                ", length=" + length +
                ", type=" + type +
                '}';
    }
}
