package org.mengdadou.test.service;

import org.mengdadou.rpc.PotatoCtxBean;
import org.mengdadou.rpc.annotation.Potato;
import org.mengdadou.rpc.annotation.PotatoCtx;
import org.mengdadou.rpc.annotation.PotatoRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mengdadou on 17-8-17.
 */
@Potato(pool = 100)
public class UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @PotatoRpc(path = "/user/register")
    public void register(@PotatoCtx PotatoCtxBean ctxBean, String password) {
        ctxBean.reset();
    }
    
    @PotatoRpc(path = "/user/v1/getName")
    public String getNameV1(long userId) {
        logger.info("accept userId {}", userId);
        return "this is v1 li ming" + userId;
    }
    
    @PotatoRpc(path = "/user/v2/getName")
    public String getNameV2(long userId) {
        logger.info("accept userId {}", userId);
        return "this is v2 li ming" + userId;
    }
    
    @PotatoRpc(path = "/save")
    public void register(String id, String name, String age) {
        logger.info("accept id   {}", id);
        logger.info("accept name {}", name);
        logger.info("accept age  {}", age);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

