public class FlyCarDecorator extends CarDecorator {
    @Override
    public void show() {
        this.getCar().show();//保持原来的功能
        this.fly();//add fly
    }

    @Override
    public void run() {

    }

    protected void fly(){
        System.out.println("Fly function add by FlyCarDecorator");
    }

    public FlyCarDecorator(Car car) {
        super(car);
    }
}
