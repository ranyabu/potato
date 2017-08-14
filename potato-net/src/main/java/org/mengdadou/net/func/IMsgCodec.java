package org.mengdadou.net.func;

import org.mengdadou.net.msg.NettyMsg;

/**
 * Created by mengdadou on 17-6-20.
 */
public interface IMsgCodec {
    byte[] toBytes(NettyMsg t);
    
    NettyMsg toObject(byte[] bytes);
}
