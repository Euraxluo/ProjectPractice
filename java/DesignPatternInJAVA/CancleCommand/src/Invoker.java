/**
 * 请求者
 */
public class Invoker {
    Command createCommand;

    public void setCreateCommand(Command createCommand) {
        this.createCommand = createCommand;
    }
    public void executeCreateCommand(String dirName){
        this.createCommand.execute(dirName);
    }
    public void undoCreateCommand(){
        createCommand.undo();
    }
}
