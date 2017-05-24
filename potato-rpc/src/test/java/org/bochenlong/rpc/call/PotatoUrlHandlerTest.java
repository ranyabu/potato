package org.bochenlong.rpc.call;

import org.bochenlong.rpc.func.impl.PotatoURLHandler;

/**
 * Created by bochenlong on 17-3-27.
 */
public class PotatoUrlHandlerTest {
    public static void main(String[] args) {
        String restURL = "potato://127.0.0.1/chKey/path/v1";
        PotatoURLHandler handler = new PotatoURLHandler();
        String a = handler.getHost(restURL);
        System.out.println(a);
        a = handler.getHostKey(restURL);
        System.out.println(a);
        a = handler.getPath(restURL);
        System.out.println(a);
    }
}
