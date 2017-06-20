package org.bochenlong.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.bochenlong.net.client.NettyClient;
import org.bochenlong.net.common.exception.RemoteException;
import org.bochenlong.net.func.DataHandler;
import org.bochenlong.net.msg.MsgFactory;
import org.bochenlong.net.msg.bean.NettyMsg;
import org.bochenlong.net.server.NettyServer;
import org.bochenlong.net.util.SpiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by bochenlong on 16-11-4.
 */
public class NettyHelper {
    
    private static Logger logger = LoggerFactory.getLogger(NettyHelper.class);
    
    private static DataHandler dataHandler = SpiUtil.getServiceImpl(DataHandler.class);
    
    private static String getIp(SocketAddress socketAddress) {
        String address = socketAddress.toString();
        address = address.substring(address.indexOf("/") + 1, address.indexOf(":"));
        return address;
    }
    
    public static void send(Channel ch, Object data) throws RemoteException {
        try {
            ChannelFuture future = ch.writeAndFlush(MsgFactory.newMsg(data));
            future.await(NettyManager.me().getSEND_TIME_OUT());
            Throwable cause = future.cause();
            if (cause != null) {
                throw cause;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RemoteException(String.format("fail sendSync message to %s, cause %s", NettyHelper.getIp(ch.remoteAddress()), throwable.getMessage()));
        }
    }
    
    public static void send(ChannelHandlerContext ctx, Object data) throws RemoteException {
        send(ctx.channel(), data);
    }
    
    // not thread safe
    // means that will create N conn to one host
    public static Channel connH(String host) {
        return new NettyClient(host).channel();
    }
    
    public static void delivery(ChannelHandlerContext ctx, NettyMsg msg) {
        logger.debug("receive msg {}", msg);
        dataHandler.execute(ctx, msg.getBody());
    }
    
    public static void startServer() {
        new NettyServer().start();
    }
    
}
