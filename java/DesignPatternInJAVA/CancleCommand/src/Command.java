/**
 * 包含撤销命令的接口
 */
public interface Command {
    /**
     * 执行命令
     */
    void execute(String dirName);

    /**
     * 撤销命令
     */
    void undo();
}
