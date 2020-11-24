/**
 * 对象结构角色
 */
public class Park  implements ParkElement{
    private String name;
    private ParkA parkA;
    private ParkB parkB;

    public Park() {
        this.parkA = new ParkA();
        this.parkB = new ParkB();
        parkB.setName("B");
        parkA.setName("A");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        parkA.accept(visitor);
        parkB.accept(visitor);
    }
}
