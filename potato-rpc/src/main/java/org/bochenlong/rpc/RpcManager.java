package org.bochenlong.rpc;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

/**
 * Created by bochenlong on 17-3-24.
 */
public class RpcManager {
    private static class Holder {
        private static RpcManager rpcM = init();

        private static RpcManager init() {
            InputStream in = RpcManager.class.getClassLoader().getResourceAsStream("rpc.yaml");
            return new Yaml().loadAs(in, RpcManager.class);
        }
    }

    public static RpcManager me() {
        return Holder.rpcM;
    }

    private static long EXECUTE_TIME_OUT = 30000;

    public static long getExecuteTimeOut() {
        return EXECUTE_TIME_OUT;
    }

    public static void setExecuteTimeOut(long executeTimeOut) {
        EXECUTE_TIME_OUT = executeTimeOut;
    }
}
