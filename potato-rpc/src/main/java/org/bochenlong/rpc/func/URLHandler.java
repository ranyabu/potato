package org.bochenlong.rpc.func;

/**
 * Created by bochenlong on 17-3-27.
 * URL :  potato://127.0.0.1/chKey/path
 */
public interface URLHandler {
    // 127.0.0.1
    String getHost(String resultURL);

    // path
    String getPath(String resultURL);

    // 127.0.0.1/chKey
    String getHostKey(String resultURL);
}
