package org.bochenlong.net.func;

import io.netty.channel.ChannelHandlerContext;
import org.bochenlong.net.msg.bean.NettyMsg;

/**
 * Created by bochenlong on 17-3-27.
 */
@FunctionalInterface
public interface DataHandler {
    void execute(ChannelHandlerContext ctx, Object data);
}
