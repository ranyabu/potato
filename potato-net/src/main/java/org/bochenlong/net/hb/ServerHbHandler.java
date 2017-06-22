package org.bochenlong.net.hb;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.bochenlong.net.msg.MsgType;
import org.bochenlong.net.msg.NettyMsg;

/**
 * Created by bochenlong on 17-6-22.
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
}
