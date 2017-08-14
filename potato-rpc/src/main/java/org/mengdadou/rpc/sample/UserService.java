package org.mengdadou.rpc.sample;

import org.mengdadou.rpc.annotation.Potato;
import org.mengdadou.rpc.annotation.PotatoRpc;

/**
 * Created by mengdadou on 17-3-23.
 */
@Potato(pool = 2)
public class UserService {
    @PotatoRpc(path = "/user/v1/getName")
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
