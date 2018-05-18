package org.mengdadou.rpc;

import org.mengdadou.net.NettyHelper;

public class PotatoRpc {
	public static void start() {
		new RpcDispatcher();
		NettyHelper.startServer();
	}
}
