package org.mengdadou.test.service;

import org.mengdadou.net.exception.RemoteException;
import org.mengdadou.rpc.PotatoRpc;
import org.mengdadou.rpc.call.CallHelper;
import org.mengdadou.rpc.exchange.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by mengdadou on 17-8-17.
 */
public class PotatoTest {
    private static Logger logger = LoggerFactory.getLogger(PotatoTest.class);
    
    public static void main(String[] args) throws RemoteException, ExecutionException, TimeoutException, InterruptedException {
        PotatoRpc.start();
    
        for (int i = 0; i < 5; i++) {
            Response sync = CallHelper.sync("potato://127.0.0.1/a/user/v2/getName", i);
            logger.info(sync.getData().toString());
            
        }
    
    }
}
