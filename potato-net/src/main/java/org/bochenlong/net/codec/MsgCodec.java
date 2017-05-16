package org.bochenlong.net.codec;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.bochenlong.net.msg.bean.NettyMsg;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bochenlong on 16-11-3.
 */
public class MsgCodec {
    private static Schema<NettyMsg> schema = RuntimeSchema.createFrom(NettyMsg.class);

    public static byte[] toBytes(NettyMsg t) {
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        return ProtostuffIOUtil.toByteArray(t, schema, buffer);
    }

    public static NettyMsg toObject(byte[] bytes) {
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
