package org.mengdadou.rpc.func;

import org.mengdadou.net.util.SpiUtil;

/**
 * Created by mengdadou on 17-5-25.
 */
public class URLHandlerUtil {
    private static IURLHandler handler = SpiUtil.getServiceImpl(IURLHandler.class);
    
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
