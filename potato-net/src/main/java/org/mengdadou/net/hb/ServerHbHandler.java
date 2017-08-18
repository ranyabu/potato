package org.mengdadou.net.hb;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.mengdadou.net.msg.MsgType;
import org.mengdadou.net.msg.NettyMsg;

/**
 * Created by mengdadou on 17-6-22.
 */
public class ServerHbHandler extends ChannelInboundHandlerAdapter implements HbHandler {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object _msg) throws Exception {
        NettyMsg msg = (NettyMsg) _msg;
        if (MsgType.BEAT.getType() == msg.getHeader().getType()) {
            ctx.writeAndFlush(newBeat());
            ReferenceCountUtil.release(msg);
            return;
        }
        ctx.fireChannelRead(_msg);
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }
}
