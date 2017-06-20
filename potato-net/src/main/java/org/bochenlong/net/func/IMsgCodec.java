package org.bochenlong.net.func;

import org.bochenlong.net.msg.bean.NettyMsg;

/**
 * Created by bochenlong on 17-6-20.
 */
public interface IMsgCodec {
    byte[] toBytes(NettyMsg t);
    
    NettyMsg toObject(byte[] bytes);
}
