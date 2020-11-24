public class Bus extends Car {
    public Bus(Engine engine) {
        super(engine);
    }

    /**
     * 这个接口中,实现了桥,在这里调用了Engine的同名函数
     */
    @Override
    public void EnginType() {
        System.out.print("BUS:  ");
        this.getEngine().EnginType();
    }
}
