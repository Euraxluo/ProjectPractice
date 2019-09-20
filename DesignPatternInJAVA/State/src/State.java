public abstract class State {
    /**
     * 环境角色,可以封装由状态的变化导致的功能变化
     */
    Context context;
    public void setContext(Context context){
        this.context = context;
    }
    public abstract void openDoor(); //开门
    public abstract void openingDoor();//开门中
    public abstract void closeDoor();//关门
    public abstract void closingDoor();//关门中
    public abstract void runing();//运行中
    public abstract void stop();//停止
}
