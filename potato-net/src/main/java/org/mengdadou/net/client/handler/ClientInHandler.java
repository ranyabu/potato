package org.mengdadou.net.client.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.mengdadou.net.NettyHelper;
import org.mengdadou.net.msg.NettyMsg;
import org.mengdadou.net.event.active.ActiveWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mengdadou on 16-11-3.
 */
public class ClientInHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ClientInHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object _msg) {
        logger.debug("channel read {}", ctx);
        NettyHelper.delivery(ctx, (NettyMsg) _msg);
        ReferenceCountUtil.release(_msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        logger.debug("channel read complete {}", ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("{} exception {}", ctx, cause);
        cause.printStackTrace();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.debug("channel active {}", ctx);
        if (!ActiveWrapper.active(ctx)) ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        logger.debug("channel registered {}", ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        logger.debug("channel unregistered {}", ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        logger.debug("channel inactive {}", ctx);
        ActiveWrapper.inactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        logger.debug("user event triggered {}", ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) {
        logger.debug("channel writability changed {}", ctx);
    }

}
