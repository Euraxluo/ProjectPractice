public class Main {

    public static void main(String[] args) {
        //初始化被访问者
        Park park = new Park();
        park.setName("innovation");

        //初始化访问者
        Visitor visitorA = new VisitorA();
        Visitor visitorB = new VisitorB();
        Visitor visitorManager = new VisitorManager();

        //公园接待访问者
        park.accept(visitorA);
        park.accept(visitorB);
        park.accept(visitorManager);
    }
}
