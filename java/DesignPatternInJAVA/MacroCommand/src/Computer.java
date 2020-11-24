/**
 * 接收者角色
 */
public class Computer {
    public void startBIOS(){
        System.out.println("Loading...");
        System.out.println("startBIOS");
    }
    public void startMBR(){
        System.out.println("startMBR");
    }
    public void startHDD(){
        System.out.println("startHDD");
    }
    public void startOS(){
        System.out.println("startOS");
        System.out.println("欢迎使用");
    }
}
