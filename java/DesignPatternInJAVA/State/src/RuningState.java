public class RuningState extends State {
    private volatile static RuningState rs;
    private RuningState(){}
    public static RuningState getInstance(){
        if(rs == null){
            synchronized (RuningState.class){
                if (rs==null){
                    rs = new RuningState();
                }
            }
        }
        return rs;
    }
    /**
     * 运行状态不能开着门
     */
    @Override
    public void openDoor() {
        System.out.println("error:运行状态不能开着门");
    }

    /**
     * 运行状态不能开门
     */
    @Override
    public void openingDoor() {
        System.out.println("error:运行状态不能开门");
    }

    /**
     * 运行状态必须关门
     */
    @Override
    public void closeDoor() {
        System.out.println("运行状态:已关门");
    }

    /**
     * 关门中无法运行
     */
    @Override
    public void closingDoor() {
        System.out.println("error:运行状态无法切换到关门中");
    }

    /**
     * 运行状态正在运行
     */
    @Override
    public void runing() {
        System.out.println("运行状态:运行中");
    }

    /**
     * 运行状态可以切换到停止
     */
    @Override
    public void stop() {
        System.out.println("状态切换:运行=>停止");
        context.setState(StopedState.getInstance());
        context.getState().stop();
    }
}
