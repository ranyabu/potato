package org.bochenlong.net.codec;

import org.bochenlong.net.func.IMsgCodec;
import org.bochenlong.net.msg.bean.NettyMsg;
import org.bochenlong.net.util.SpiUtil;

/**
 * Created by bochenlong on 17-6-20.
 */
public class MsgCodecWrapper {
    private static IMsgCodec msgCodec = SpiUtil.getServiceImpl(IMsgCodec.class);
    
    public static byte[] toBytes(NettyMsg t) {
        return msgCodec.toBytes(t);
    }
    
    public static NettyMsg toObject(byte[] bytes) {
        return msgCodec.toObject(bytes);
    }
}
