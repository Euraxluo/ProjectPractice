public class CommandA implements Command {
    //持有对应的接收者
    private ReceiverA receiverA;
    private String task;


    public CommandA(ReceiverA receiverA, String task) {
        this.receiverA = receiverA;
        this.task = task;
    }

    @Override
    public void execute() {
        receiverA.work(this.task);
    }
}
