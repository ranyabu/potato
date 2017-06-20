package org.bochenlong.net.func;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by bochenlong on 17-1-9.
 */
public interface IActive {
    boolean active(ChannelHandlerContext ctx);
    
    void inactive(ChannelHandlerContext ctx);
}
