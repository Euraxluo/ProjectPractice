public class SwimCarDecorator extends CarDecorator {
    @Override
    public void show() {
        this.getCar().show();//保持以前的功能
        this.swim();//添加新功能
    }

    private void swim() {
        System.out.println("Swim function add by SwimCarDecorator");
    }

    public SwimCarDecorator(Car car) {
        super(car);
    }

    @Override
    public void run() {
    }

}
