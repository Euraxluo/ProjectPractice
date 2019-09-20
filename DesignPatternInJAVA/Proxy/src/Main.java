import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        //自己代理模式的调用方式
//        ProxySubject proxySubject = new ProxySubject();
//        proxySubject.eat();

        //动态代理
        RealSubject realSubject = new RealSubject();
        ProxyHandler proxyHandler = new ProxyHandler();
        proxyHandler.setRealSubject(realSubject);
        Subject proxySubject = (Subject) Proxy.newProxyInstance(RealSubject.class.getClassLoader(),RealSubject.class.getInterfaces(),proxyHandler);
        proxySubject.eat();
    }
}
