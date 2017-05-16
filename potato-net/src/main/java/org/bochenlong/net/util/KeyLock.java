package org.bochenlong.net.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by bochenlong on 16-12-27.
 */
public class KeyLock {
    private static Map<String, byte[]> objectLockMap = new ConcurrentHashMap<>();
    
    public static boolean lock(String key) {
        return objectLockMap.putIfAbsent(key, new byte[1]) == null;
    }
    
    public static boolean tryLock(String key, long timeout, TimeUnit unit) {
        long current = System.currentTimeMillis();
        timeout = unit.toMillis(timeout);
        boolean flag = true;
        while (!KeyLock.lock(key)) {
            if (System.currentTimeMillis() - current >= timeout) {
                flag = false;
                break;
            }
        }
        return flag;
    }
    
    public static void unlock(String key) {
        if (objectLockMap.containsKey(key))
            objectLockMap.remove(key);
    }
}
