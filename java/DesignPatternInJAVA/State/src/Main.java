public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.setState(StopedState.getInstance());
        context.openingDoor();
        context.openDoor();
        context.closingDoor();
        context.closeDoor();
        context.runing();
        context.stop();
    }
}
