package org.bochenlong.net.hb;

import org.bochenlong.net.msg.Header;
import org.bochenlong.net.msg.MsgType;
import org.bochenlong.net.msg.NettyMsg;

/**
 * Created by bochenlong on 17-6-22.
 */
public interface HbHandler {
    default NettyMsg newBeat() {
        Header header = new Header(MsgType.BEAT.getType());
        return new NettyMsg(header);
    }
}
