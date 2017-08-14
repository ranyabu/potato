package org.mengdadou.net.codec;

import org.mengdadou.net.func.IMsgCodec;
import org.mengdadou.net.msg.NettyMsg;
import org.mengdadou.net.util.SpiUtil;

/**
 * Created by mengdadou on 17-6-20.
 */
public class MsgCodecWrapper {
    private static IMsgCodec msgCodec = SpiUtil.getServiceImpl(IMsgCodec.class);
    
    static byte[] toBytes(NettyMsg t) {
        return msgCodec.toBytes(t);
    }
    
    static NettyMsg toObject(byte[] bytes) {
        return msgCodec.toObject(bytes);
    }
}
