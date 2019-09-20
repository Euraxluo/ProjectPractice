public class TVOnCommand  implements Command{
    private TV tv;

    public TVOnCommand(TV tv) {
        this.tv = tv;
    }

    @Override
    public void excute() {
        tv.on();
    }
}
