public class CommandB implements Command {
    //持有对应的接收者
    private ReceiverB receiverB;
    private String task;


    public CommandB(ReceiverB receiverB, String task) {
        this.receiverB = receiverB;
        this.task = task;
    }

    @Override
    public void execute() {
        receiverB.work(this.task);
    }
}
