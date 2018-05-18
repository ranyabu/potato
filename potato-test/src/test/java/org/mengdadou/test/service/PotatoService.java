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
public class PotatoService {
	private static Logger logger = LoggerFactory.getLogger(PotatoService.class);
	
	@PotatoRpc(path = "/user/v1/getName")
	public String getNameV1(long userId) {
		System.out.println(String.format("accept userId %s", userId));
		return "this is v1 li ming" + userId;
	}
	
	@PotatoRpc(path = "/user/v2/getName")
	public String getNameV2(@PotatoCtx PotatoCtxBean ctx, long userId) {
		System.out.println(String.format("accept userId %s from %s", userId, ctx.getRemote()));
		return "this is v2 li ming" + userId;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}

