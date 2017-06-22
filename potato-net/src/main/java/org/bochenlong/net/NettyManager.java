package org.bochenlong.net;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

/**
 * Created by bochenlong on 16-10-24.
 */
public class NettyManager {
    private static class Holder {
        private static NettyManager manager = init();
        
        private static NettyManager init() {
            InputStream in = NettyManager.class.getClassLoader().getResourceAsStream("netty.yaml");
            return new Yaml().loadAs(in, NettyManager.class);
        }
    }
    
    public static NettyManager me() {
        return Holder.manager;
    }
    
    private static String DEFAULT_HOST = "0.0.0.0";
    private static int DEFAULT_PORT = 8989;
    
    private static int MSG_MAX_LEN = 1024 * 1024;
    private static int MSG_LEN_OFFSET = 0;
    private static int MSG_LEN_FIELD = 4;
    private static int MSG_LEN_ADJUSTMENT = -4;
    
    private static int BACKLOG_SIZE = 1204;
    
    private static int CONNECT_TIME_OUT = 1000 * 30;
    private static int SEND_TIME_OUT = 1000 * 15;
    private static int RETRY_TIME = 1000 * 60;
    
    private static int IDLE_TIME_OUT = 1000 * 5;
    
    public String getDEFAULT_HOST() {
        return DEFAULT_HOST;
    }
    
    public void setDEFAULT_HOST(String DEFAULT_HOST) {
        NettyManager.DEFAULT_HOST = DEFAULT_HOST;
    }
    
    public int getDEFAULT_PORT() {
        return DEFAULT_PORT;
    }
    
    public void setDEFAULT_PORT(int DEFAULT_PORT) {
        NettyManager.DEFAULT_PORT = DEFAULT_PORT;
    }
    
    public int getMSG_MAX_LEN() {
        return MSG_MAX_LEN;
    }
    
    public void setMSG_MAX_LEN(int MSG_MAX_LEN) {
        NettyManager.MSG_MAX_LEN = MSG_MAX_LEN;
    }
    
    public int getMSG_LEN_OFFSET() {
        return MSG_LEN_OFFSET;
    }
    
    public void setMSG_LEN_OFFSET(int MSG_LEN_OFFSET) {
        NettyManager.MSG_LEN_OFFSET = MSG_LEN_OFFSET;
    }
    
    public int getMSG_LEN_FIELD() {
        return MSG_LEN_FIELD;
    }
    
    public void setMSG_LEN_FIELD(int MSG_LEN_FIELD) {
        NettyManager.MSG_LEN_FIELD = MSG_LEN_FIELD;
    }
    
    public int getMSG_LEN_ADJUSTMENT() {
        return MSG_LEN_ADJUSTMENT;
    }
    
    public void setMSG_LEN_ADJUSTMENT(int MSG_LEN_ADJUSTMENT) {
        NettyManager.MSG_LEN_ADJUSTMENT = MSG_LEN_ADJUSTMENT;
    }
    
    public int getBACKLOG_SIZE() {
        return BACKLOG_SIZE;
    }
    
    public void setBACKLOG_SIZE(int BACKLOG_SIZE) {
        NettyManager.BACKLOG_SIZE = BACKLOG_SIZE;
    }
    
    public int getCONNECT_TIME_OUT() {
        return CONNECT_TIME_OUT;
    }
    
    public void setCONNECT_TIME_OUT(int CONNECT_TIME_OUT) {
        NettyManager.CONNECT_TIME_OUT = CONNECT_TIME_OUT;
    }
    
    public int getSEND_TIME_OUT() {
        return SEND_TIME_OUT;
    }
    
    public void setSEND_TIME_OUT(int SEND_TIME_OUT) {
        NettyManager.SEND_TIME_OUT = SEND_TIME_OUT;
    }
    
    public int getRETRY_TIME() {
        return RETRY_TIME;
    }
    
    public void setRETRY_TIME(int RETRY_TIME) {
        NettyManager.RETRY_TIME = RETRY_TIME;
    }
    
    public int getIDLE_TIME_OUT() {
        return IDLE_TIME_OUT;
    }
    
    public void setIDLE_TIME_OUT(int idleTimeOut) {
        NettyManager.IDLE_TIME_OUT = idleTimeOut;
    }
}
