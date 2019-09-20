/**
 * 具体命令
 */
public class MCommand implements Command {
    private Computer computer;

    public MCommand(Computer computer) {
        this.computer = computer;
    }

    @Override
    public void execute() {
        computer.startMBR();
    }
}
