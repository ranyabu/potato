package org.bochenlong.net.event.active.impl;

import io.netty.channel.ChannelHandlerContext;
import org.bochenlong.net.NettyHelper;
import org.bochenlong.net.event.active.IActive;

import java.util.HashSet;

/**
 * Created by bochenlong on 16-12-22.
 */
public class WhiteList implements IActive {

    @Override
    public boolean active(ChannelHandlerContext ctx) {
        return true;
    }
    
    @Override
    public void inactive(ChannelHandlerContext ctx) {
    }
}
