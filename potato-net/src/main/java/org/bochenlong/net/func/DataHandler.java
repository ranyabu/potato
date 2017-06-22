package org.bochenlong.net.func;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by bochenlong on 17-3-27.
 */
@FunctionalInterface
public interface DataHandler {
    void execute(ChannelHandlerContext ctx, Object data);
}
