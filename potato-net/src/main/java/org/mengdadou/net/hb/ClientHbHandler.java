package org.mengdadou.net.hb;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.mengdadou.net.msg.MsgType;
import org.mengdadou.net.msg.NettyMsg;

/**
 * Created by mengdadou on 17-6-21.
 */
public class ClientHbHandler extends ChannelInboundHandlerAdapter implements HbHandler {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object _msg) throws Exception {
        NettyMsg msg = (NettyMsg) _msg;
        if (MsgType.BEAT.getType() == msg.getHeader().getType()) {
            ReferenceCountUtil.release(msg);
            return;
        }
        ctx.fireChannelRead(_msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object _evt) throws Exception {
        if (_evt instanceof IdleStateEvent) {
            IdleStateEvent evt = (IdleStateEvent) _evt;
            if (evt.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(newBeat());
                return;
            }
        }
        super.userEventTriggered(ctx, _evt);
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }

}
