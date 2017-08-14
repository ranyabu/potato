package org.mengdadou.net.hb;

import org.mengdadou.net.msg.Header;
import org.mengdadou.net.msg.MsgType;
import org.mengdadou.net.msg.NettyMsg;

/**
 * Created by mengdadou on 17-6-22.
 */
public interface HbHandler {
    default NettyMsg newBeat() {
        Header header = new Header(MsgType.BEAT.getType());
        return new NettyMsg(header);
    }
}
