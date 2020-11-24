/**
 * 具体命令
 */
public class HCommand implements Command {
    private Computer computer;

    public HCommand(Computer computer) {
        this.computer = computer;
    }

    @Override
    public void execute() {
        computer.startHDD();
    }
}
