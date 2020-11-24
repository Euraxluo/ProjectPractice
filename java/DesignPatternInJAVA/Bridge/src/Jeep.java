public class Jeep extends Car {

    public Jeep(Engine engine) {
        super(engine);
    }

    @Override
    public void EnginType() {
        System.out.print("JEEP:  ");
        this.getEngine().EnginType();
    }
}
