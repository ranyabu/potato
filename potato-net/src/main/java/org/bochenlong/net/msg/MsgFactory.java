package org.bochenlong.net.msg;


/**
 * Created by bochenlong on 17-1-3.
 */
public class MsgFactory {
    public static NettyMsg newMsg(Object o) {
        Header header = new Header();
        header.setType(MsgType.BIZ.getType());
        return new NettyMsg(header, o);
    }
}
