/**
 * 抽象访问者角色
 */
public interface Visitor {
    public void visit(Park park);
    public void visit(ParkA parkA);
    public void visit(ParkB parkB);
}
