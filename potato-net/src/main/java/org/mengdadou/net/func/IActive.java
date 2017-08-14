package org.mengdadou.net.func;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by mengdadou on 17-1-9.
 */
public interface IActive {
    boolean active(ChannelHandlerContext ctx);
    
    void inactive(ChannelHandlerContext ctx);
}
