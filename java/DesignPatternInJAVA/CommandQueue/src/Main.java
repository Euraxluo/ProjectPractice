public class Main {

    public static void main(String[] args) {
        ReceiverA receiverA = new ReceiverA();
        ReceiverB receiverB = new ReceiverB();

        CommandA commandA = new CommandA(receiverA,"后台");
        CommandB commandB = new CommandB(receiverB,"前端");

        CommandQueue commandQueue = new CommandQueue();
        commandQueue.addCommand(commandA);
        commandQueue.addCommand(commandB);

        Requester requester = new Requester(commandQueue);
        requester.execute();
    }
}
