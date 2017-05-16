package org.bochenlong.rpc.sample;

import org.bochenlong.rpc.annotation.Potato;
import org.bochenlong.rpc.annotation.PotatoRpc;
import org.bochenlong.rpc.exchange.Request;
import org.bochenlong.rpc.exchange.Response;

/**
 * Created by bochenlong on 17-3-23.
 */
@Potato(pool = 2)
public class UserService {
    @PotatoRpc(path = "/getName")
    public String getName(long userId) {
        return "this is li ming";
    }

    @PotatoRpc(path = "/say")
    public void say(String hello) {
        System.out.println(String.format("accept %s", hello));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
