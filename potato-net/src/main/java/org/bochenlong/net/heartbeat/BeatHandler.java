package org.bochenlong.net.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.ReferenceCountUtil;
import org.bochenlong.net.msg.Header;
import org.bochenlong.net.msg.MsgType;
import org.bochenlong.net.msg.NettyMsg;

import java.util.function.Supplier;

/**
 * Created by bochenlong on 17-6-21.
 */
public class BeatHandler extends IdleStateHandler {
    public BeatHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }
    
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
                ctx.writeAndFlush(beatMsg.get());
                return;
            }
        }
        super.userEventTriggered(ctx, _evt);
    }
    
    private Supplier<NettyMsg> beatMsg = () -> {
        Header header = new Header(MsgType.BEAT.getType());
        return new NettyMsg(header);
    };
}
