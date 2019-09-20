/**
 * 请求者,针对CommandQueue编程
 */
public class Requester {
    private CommandQueue commandQueue;

    public Requester(CommandQueue commandQueue) {
        this.commandQueue = commandQueue;
    }

    public void setCommandQueue(CommandQueue commandQueue) {
        this.commandQueue = commandQueue;
    }
    //调用CommandQueue的execute方法
    public void execute(){
        commandQueue.execute();
    }
}
