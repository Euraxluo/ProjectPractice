public class ProxySubject implements Subject {
    private RealSubject realSubject;
    @Override
    public void eat() {
        make();//做饭
        if(realSubject == null){
            realSubject = new RealSubject();
        }
        realSubject.eat();
        wash();//洗碗
    }

    public void make(){
        System.out.println("making");
    }
    public void wash(){
        System.out.println("washing");
    }
}
