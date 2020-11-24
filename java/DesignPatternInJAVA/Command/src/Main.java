public class Main {

    public static void main(String[] args) {
        //创建者
        TV receiver = new TV();
        //创建命令对象,设定他的接收者
        Command on_command = new TVOnCommand(receiver);
        Command off_command = new TVOffCommand(receiver);
        //命令控制对象invoker,把命令通过构造方法设置进去
        RemoteControl invoker = new RemoteControl(on_command,off_command);
        invoker.trunOn();
        invoker.trunOff();

    }
}
