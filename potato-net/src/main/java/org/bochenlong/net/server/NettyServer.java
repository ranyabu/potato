package org.bochenlong.net.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.bochenlong.net.NettyManager;
import org.bochenlong.net.codec.MsgDecoder;
import org.bochenlong.net.codec.MsgEncoder;
import org.bochenlong.net.server.handler.ServerActiveHandler;
import org.bochenlong.net.server.handler.ServerBizHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bochenlong on 16-11-3.
 */
public class NettyServer {
    private static Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    
    private void bind(int port) {
        try {
            bossGroup = new NioEventLoopGroup();
            workGroup = new NioEventLoopGroup();
            
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    // for bossGroup
                    .option(ChannelOption.SO_BACKLOG, NettyManager.me().getBACKLOG_SIZE())
                    .option(ChannelOption.SO_KEEPALIVE, true)// 保活
                    // for workGroup
                    .childOption(ChannelOption.TCP_NODELAY, false)// 有数据就发，默认false
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new MsgDecoder(
                                            NettyManager.me().getMSG_MAX_LEN(),
                                            NettyManager.me().getMSG_LEN_OFFSET(),
                                            NettyManager.me().getMSG_LEN_FIELD(),
                                            NettyManager.me().getMSG_LEN_ADJUSTMENT()));
                            ch.pipeline().addLast(new MsgEncoder());
                            ch.pipeline().addLast(new ServerActiveHandler());
                            ch.pipeline().addLast(new ServerBizHandler());
                        }
                    });
            
            // 绑定端口，等待启动成功
            ChannelFuture future = bootstrap.bind(NettyManager.me().getDEFAULT_HOST(), port).sync();
            logger.info("server start ok : {} - {}", NettyManager.me().getDEFAULT_HOST(), port);
            // 监听关闭事件
            future.channel().closeFuture().addListener(a -> {
                if (a.isDone()) {
                    logger.info("server stop : {} - {}", NettyManager.me().getDEFAULT_HOST(), port);
                    // 关闭资源
                    this.stop();
                }
            });
        } catch (Exception e) {
            logger.error("server start exception : {} - {} / {}", NettyManager.me().getDEFAULT_HOST(), port, e.getMessage());
            e.printStackTrace();
            // 关闭资源
            this.stop();
        }
    }
    
    private void start(int port) {
        bind(port);
    }
    
    public void start() {
        start(NettyManager.me().getDEFAULT_PORT());
    }
    
    protected void stop() {
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        logger.error("server stop over");
    }
    
}
