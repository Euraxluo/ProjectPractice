package com.mmall.common;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TokenCache
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-11 下午9:56
 */
public class TokenCache {
    //申明日志
    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);
    public static final String TOKEN_PREFIX = "token_";
    //申明一个内存块作为缓存
    //内存块使用LRU算法进行管理
    //初始化10000,最大值为10000,如果超过10000,就会使用LRU算法,有效期是12个小时
    private static LoadingCache<String,String> loadingCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(1000).expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                //默认的数据加载实现,当调用get取值时,没有找到对应的值,就会调用这个类进行加载
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    //外部调用的方法,可以把token交给Guava来管理
    public static void setKey(String key,String value){
        loadingCache.put(key, value);
    }

    public static String getKey(String key){
        String value = null;
        try {
            if( "null".equals(loadingCache.get(key)) ){
                return null;
            }
            return loadingCache.get(key);
        } catch (ExecutionException e) {
            logger.error("loadingCache get error",e);
        }
        return null;
    }





}
