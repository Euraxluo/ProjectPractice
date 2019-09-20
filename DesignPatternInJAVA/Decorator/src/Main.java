public class Main {

    public static void main(String[] args) {
        System.out.println("Bash car function:-----");
        Car car = new BashCar();//创建一个基础的Car
        car.show();
        System.out.println("Bash car add fly function:-----");
        Car flyCar = new FlyCarDecorator(car);
        flyCar.show();
        System.out.println("FlyCar car add swim function:-----");
        Car swimCar = new SwimCarDecorator(flyCar);
        swimCar.show();
    }
}
