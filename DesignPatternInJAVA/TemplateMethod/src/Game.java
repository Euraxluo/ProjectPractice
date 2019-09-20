/**
 * 抽象类
 */
public abstract class Game {
    /**
     * 抽象具体步骤方法,实现细节,需要由子类实现
     */
    abstract void init();
    abstract void start();
    abstract void end();

    /**
     * 模板方法,规定了具体方法的运行步骤(次序)
     */
    public final void play(){
        init();
        start();
        end();
    }
}
