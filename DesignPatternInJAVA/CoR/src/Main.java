public class Main {

    public static void main(String[] args) {
        CarHandler doA = new CarDoAHandler();
        CarHandler doB = new CarDoBHandler();
        CarHandler doC = new CarDoCHandler();

        doA.setNextHandler(doB).setNextHandler(doC);
        doA.CarHandler();

        doA.setNextHandler(doB);
        doB.setNextHandler(doC);
//        doC.setNextHandler(doA); 这里会报堆栈溢出
        doA.CarHandler();

    }
}
