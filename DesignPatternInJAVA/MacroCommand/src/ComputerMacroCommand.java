import java.util.ArrayList;

/**
 * 宏命令接口实现
 */
public class ComputerMacroCommand implements MacroCommand {
    private ArrayList<Command> commands;

    public ComputerMacroCommand() {
        this.commands = new ArrayList<Command>();
    }

    @Override
    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public void removeCommand(Command command) {
        commands.remove(command);
    }

    @Override
    public void execute() {
        for (int i=0;i< commands.size();i++){
            commands.get(i).execute();
        }
    }
}
