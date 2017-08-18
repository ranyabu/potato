package org.mengdadou.test.service;

import org.mengdadou.net.NettyHelper;
import org.mengdadou.net.exception.RemoteException;
import org.mengdadou.rpc.RpcDispatcher;
import org.mengdadou.rpc.call.CallHelper;
import org.mengdadou.rpc.exchange.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by mengdadou on 17-8-17.
 */
public class PotatoPressTest {
    private static Logger logger = LoggerFactory.getLogger(PotatoPressTest.class);
    
    public static void main(String[] args) throws RemoteException, ExecutionException, TimeoutException, InterruptedException {
        RpcDispatcher dispatcher = new RpcDispatcher();
        NettyHelper.startServer();
        for (int i = 0; i < 1000; i++) {
            Response sync = CallHelper.sync("potato://127.0.0.1/a/user/v1/getName", i);
            logger.info(sync.getData().toString());
            sync = CallHelper.sync("potato://127.0.0.1/b/user/v2/getName", i);
            logger.info(sync.getData().toString());
        }
    }
}
