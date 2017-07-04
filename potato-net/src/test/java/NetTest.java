import org.bochenlong.net.client.NettyClient;
import org.bochenlong.net.msg.Header;
import org.bochenlong.net.msg.MsgType;
import org.bochenlong.net.msg.NettyMsg;
import org.bochenlong.net.server.NettyServer;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * Created by bochenlong on 17-6-22.
 */
public class NetTest {
    @Test
    public void netOK() throws InterruptedException {
    
//        NettyServer server = new NettyServer();
//        server.start();
//        NettyClient client = new NettyClient("127.0.0.1");
//        Header header = new Header(MsgType.BEAT.getType());
//        NettyMsg msg = new NettyMsg(header);
//
//        client.channel().writeAndFlush(msg);
//        Thread.sleep(10000000L);
    
        InetSocketAddress inetSocketAddress = new InetSocketAddress(44444);
        System.out.println(inetSocketAddress.getHostName());
    }
}
