package org.mengdadou.net.msg;


/**
 * Created by mengdadou on 17-1-3.
 */
public class MsgFactory {
    public static NettyMsg newMsg(Object o) {
        Header header = new Header(MsgType.BIZ.getType());
        return new NettyMsg(header, o);
    }
}
