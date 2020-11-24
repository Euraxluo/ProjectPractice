/**
 * 宏命令接口
 */
public interface MacroCommand extends Command {
    /**
     * 添加宏命令
     * @param command
     */
    public void addCommand(Command command);

    /**
     * 删除宏命令
     * @param command
     */
    public void removeCommand(Command command);
}
