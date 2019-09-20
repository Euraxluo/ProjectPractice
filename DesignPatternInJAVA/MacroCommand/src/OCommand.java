/**
 * 具体命令
 */
public class OCommand implements Command {
    private Computer computer;

    public OCommand(Computer computer) {
        this.computer = computer;
    }

    @Override
    public void execute() {
        computer.startOS();
    }
}
