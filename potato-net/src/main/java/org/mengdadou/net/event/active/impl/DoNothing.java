package org.mengdadou.net.event.active.impl;

import io.netty.channel.ChannelHandlerContext;
import org.mengdadou.net.func.IActive;

/**
 * Created by mengdadou on 16-12-22.
 */
public class DoNothing implements IActive {

    @Override
    public boolean active(ChannelHandlerContext ctx) {
        return true;
    }
    
    @Override
    public void inactive(ChannelHandlerContext ctx) {
    }
}
