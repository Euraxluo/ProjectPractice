public class StopedState extends State {
    private volatile static StopedState ss;
    private StopedState() {}
    public static StopedState getInstance(){
        if(ss ==null){
            synchronized (StopedState.class){
                if(ss==null){
                    ss = new StopedState();
                }
            }
        }
        return ss;
    }

    /**
     * 停止状态可以开门上下客
     */
    @Override
    public void openDoor() {
        System.out.println("停止状态:已开门");
        System.out.println("停止状态:上下客人");
    }

    /**
     * 停止状态可以正开门
     */
    @Override
    public void openingDoor() {
        System.out.println("停止状态:开门中");
    }

    /**
     * 关好门就算在运行中
     */
    @Override
    public void closeDoor() {
        System.out.println("状态切换:停止=>运行");
        context.setState(RuningState.getInstance());
        context.getState().closeDoor();
    }

    /**
     * 停止状态才能关门动作
     */
    @Override
    public void closingDoor() {
        System.out.println("停止状态:关门中");
    }

    /**
     * 停止状态可以切换到运行状态
     */
    @Override
    public void runing() {
        System.out.println("状态切换:停止=>运行");
        context.setState(RuningState.getInstance());
        context.getState().runing();
    }

    /**
     * 此刻正停止着
     */
    @Override
    public void stop() {
        System.out.println("停止状态:停止中");
    }
}
