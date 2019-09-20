/**
 * 具体命令
 */
public class BCommand implements Command {
    private Computer computer;

    public BCommand(Computer computer) {
        this.computer = computer;
    }

    @Override
    public void execute() {
        computer.startBIOS();
    }
}
