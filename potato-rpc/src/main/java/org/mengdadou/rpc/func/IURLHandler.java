package org.mengdadou.rpc.func;

/**
 * Created by mengdadou on 17-3-27.
 * URL :  potato://127.0.0.1/chKey/path
 */
public interface IURLHandler {
    // 127.0.0.1
    String getHost(String resultURL);

    // path
    String getPath(String resultURL);

    // 127.0.0.1/chKey
    String getHostKey(String resultURL);
}
