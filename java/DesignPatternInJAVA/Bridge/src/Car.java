public abstract class Car {
    private Engine engine;//引擎

    public Car(Engine engine) {
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    //这就是桥
    public abstract void EnginType();//引擎类型
}
