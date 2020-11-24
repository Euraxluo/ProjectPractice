import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {
    private Object realSubject;

    public void setRealSubject(Object realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        make();
        Object res = method.invoke(realSubject, args);
        wash();
        return res;
    }
    public void make(){
        System.out.println("making");
    }
    public void wash(){
        System.out.println("washing");
    }
}
