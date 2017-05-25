package org.bochenlong.rpc.call;

import org.bochenlong.net.NettyHelper;
import org.bochenlong.net.common.exception.RemoteException;
import org.bochenlong.rpc.RpcDispatcher;
import org.bochenlong.rpc.exchange.Response;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by bochenlong on 17-3-27.
 */
public class CallHelperTest {
    public static void main(String[] args) throws RemoteException, ExecutionException, TimeoutException, InterruptedException {
        RpcDispatcher dispatcher = new RpcDispatcher();
        NettyHelper.startServer();
        Response sync = CallHelper.sync("potato://127.0.0.1/a/user/v1/getName", 1);
        System.out.println(sync.getData());
         sync = CallHelper.sync("potato://127.0.0.1/b/getName", 1);
        System.out.println(sync.getData());
    }
}
