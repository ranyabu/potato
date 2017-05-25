package org.bochenlong.rpc.func;

import org.bochenlong.net.util.SpiUtil;

/**
 * Created by bochenlong on 17-5-25.
 */
public class URLHandlerUtil {
    private static URLHandler handler = SpiUtil.getServiceImpl(URLHandler.class);
    
    public static String getHost(String resultURL) {
        return handler.getHost(resultURL);
    }
    
    public static String getPath(String resultURL) {
        return handler.getPath(resultURL);
    }
    
    public static String getHostKey(String resultURL) {
        return handler.getHostKey(resultURL);
    }
}
