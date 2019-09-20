import java.util.ArrayList;

public class CommandQueue {
    private ArrayList<Command> commands = new ArrayList<Command>();
    public void addCommand(Command command){
        commands.add(command);
    }
    public void removeCommand(Command command){
        commands.remove(command);
    }
    //循环调用每一个命令对象的execute方法
    public void execute(){
        for (Command cmd:commands)  {
            cmd.execute();
        }
    }
}
