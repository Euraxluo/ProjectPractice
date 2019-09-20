public class Main {

    public static void main(String[] args) {
        //创建接收者
        MakeDir makeDir = new MakeDir();
        //创建具体的命令对象并指定接收者
        Command createCommand = new CreateDirCommand(makeDir);
        //创建请求者,并设置接受者的命令
        Invoker invoker = new Invoker();
        invoker.setCreateCommand(createCommand);
        //创建目录
        invoker.executeCreateCommand("C:\\home\\test1");
        invoker.executeCreateCommand("C:\\home\\test2");
        invoker.executeCreateCommand("C:\\home\\test3");
        invoker.executeCreateCommand("C:\\home\\test4");
        //多次撤销
        invoker.undoCreateCommand();
        invoker.undoCreateCommand();
        invoker.undoCreateCommand();
        invoker.undoCreateCommand();

    }
}
