public class Main {

    public static void main(String[] args) {
        //创建接收者
        Computer computer = new Computer();
        //创建具体的命令对象,设置接收者
        Command bc = new BCommand(computer);
        Command mc = new MCommand(computer);
        Command hc = new HCommand(computer);
        Command oc = new OCommand(computer);
        //宏命令对象,把具体的命令对象设置进去
        MacroCommand invoker = new ComputerMacroCommand();
        invoker.addCommand(bc);
        invoker.addCommand(mc);
        invoker.addCommand(hc);
        invoker.addCommand(oc);
        //启动
        invoker.execute();

    }
}
