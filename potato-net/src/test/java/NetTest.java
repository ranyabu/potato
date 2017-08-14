import org.mengdadou.net.client.NettyClient;
import org.mengdadou.net.msg.Header;
import org.mengdadou.net.msg.MsgType;
import org.mengdadou.net.msg.NettyMsg;
import org.mengdadou.net.server.NettyServer;
import org.junit.Test;

/**
 * Created by mengdadou on 17-6-22.
 */
public class NetTest {
    @Test
    public void netOK() throws InterruptedException {

        NettyServer server = new NettyServer();
        server.start();
        NettyClient client = new NettyClient("127.0.0.1");
        Header header = new Header(MsgType.BEAT.getType());
        NettyMsg msg = new NettyMsg(header);

        client.channel().writeAndFlush(msg);
        Thread.sleep(10000000L);
    
    }
}
