public class BashCar implements Car {
    @Override
    public void run() {
        System.out.println("Bash Car can run");
    }

    @Override
    public void show() {
        this.run();
    }
}
