package cn.euraxluo.passbook.merchants.security;

/**
 * merchants
 * cn.euraxluo.passbook.merchants.security
 * AccessContext
 * 2019/12/26 11:44
 * author:Euraxluo
 * 利用ThreadLocal 用来存储每个线程的Token信息
 */
public class AccessContext {
    private static final ThreadLocal<String> threadToken = new ThreadLocal<String>();

    public static String getThreadToken() {
        return threadToken.get();
    }
    public static void setThreadToken(String tokenStr) {
        threadToken.set(tokenStr);
    }
    public static void clearThreadToken(){
        threadToken.remove();
    }


}
