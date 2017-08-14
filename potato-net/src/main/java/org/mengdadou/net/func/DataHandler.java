package org.mengdadou.net.func;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by mengdadou on 17-3-27.
 */
@FunctionalInterface
public interface DataHandler {
    void execute(ChannelHandlerContext ctx, Object data);
}
