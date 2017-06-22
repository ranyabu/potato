package org.bochenlong.net.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by bochenlong on 17-6-21.
 */
public class PotatoHandler extends IdleStateHandler {
    public PotatoHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object _evt) throws Exception {
        if (_evt instanceof IdleStateEvent) {
            IdleStateEvent evt = (IdleStateEvent) _evt;
            if (evt.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush();
            }
        }
        super.userEventTriggered(ctx, _evt);
    }
}
