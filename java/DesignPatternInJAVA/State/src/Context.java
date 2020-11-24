/**
 * 场景类,用于切换状态
 */
public class Context{
    private State state;//状态

    public State getState() {
        return state;
    }

    /**
     * 状态转移最重要的函数,需要使用setContext把环境通知给各个状态实现类中
     * @param state
     */
    public void setState(State state) {
        this.state = state;
        this.state.setContext(this);
    }


    public void openDoor() {
        getState().openDoor();

    }

    //电梯的状态以及动作


    public void openingDoor() {
        getState().openingDoor();
    }

    public void closeDoor() {
        getState().closeDoor();
    }

    public void closingDoor() {
        getState().closingDoor();
    }

    public void runing() {
        getState().runing();
    }

    public void stop() {
        getState().stop();
    }
}
