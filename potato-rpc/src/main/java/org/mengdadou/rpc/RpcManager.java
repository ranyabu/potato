package org.mengdadou.rpc;

import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;

/**
 * Created by mengdadou on 17-3-24.
 */
public class RpcManager {
    
    private long EXECUTE_TIME_OUT = 30000;
    private int ID_WORKER_SEQ1 = 1;
    private int ID_WORKER_SEQ2 = 1;
    
    private static class Holder {
        private static RpcManager rpcManager = new RpcManager();
    }
    
    public static RpcManager singleton() {
        return Holder.rpcManager;
    }
    
    
    private RpcManager() {
        //noinspection unchecked
        LinkedHashMap<String, Object> conf =
                (LinkedHashMap<String, Object>) new Yaml().load(RpcManager.class.getClassLoader().getResourceAsStream("rpc.yaml"));
        this.EXECUTE_TIME_OUT = (int) conf.getOrDefault("EXECUTE_TIME_OUT", "30000");
        this.ID_WORKER_SEQ1 = (int) conf.getOrDefault("ID_WORKER_SEQ1", 1);
        this.ID_WORKER_SEQ2 = (int) conf.getOrDefault("ID_WORKER_SEQ2", 1);
    }
    
    public long getEXECUTE_TIME_OUT() {
        return EXECUTE_TIME_OUT;
    }
    
    public int getID_WORKER_SEQ1() {
        return ID_WORKER_SEQ1;
    }
    
    public int getID_WORKER_SEQ2() {
        return ID_WORKER_SEQ2;
    }
    
}
