package org.mengdadou.net.codec;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.mengdadou.net.func.IMsgCodec;
import org.mengdadou.net.msg.NettyMsg;

/**
 * Created by mengdadou on 16-11-3.
 */
public class MsgCodec implements IMsgCodec {
    private Schema<NettyMsg> schema = RuntimeSchema.createFrom(NettyMsg.class);
    
    public byte[] toBytes(NettyMsg t) {
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        return ProtostuffIOUtil.toByteArray(t, schema, buffer);
    }
    
    public NettyMsg toObject(byte[] bytes) {
        try {
            NettyMsg msg = new NettyMsg();
            ProtostuffIOUtil.mergeFrom(bytes, msg, schema);
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
