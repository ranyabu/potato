package org.mengdadou.net.event.active;

import io.netty.channel.ChannelHandlerContext;
import org.mengdadou.net.func.IActive;
import org.mengdadou.net.util.SpiUtil;

/**
 * Created by mengdadou on 17-1-9.
 */
public class ActiveWrapper {
    private static IActive active = SpiUtil.getServiceImpl(IActive.class);
    
    public static boolean active(ChannelHandlerContext ctx) {
        return active.active(ctx);
    }
    
    public static void inactive(ChannelHandlerContext ctx) {
        active.inactive(ctx);
    }
}
