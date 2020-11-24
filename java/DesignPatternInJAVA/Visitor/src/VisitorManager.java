public class VisitorManager implements Visitor {
    @Override
    public void visit(Park park) {
        System.out.println("检查"+park.getName()+"卫生");
    }

    @Override
    public void visit(ParkA parkA) {
        System.out.println("检查"+parkA.getName()+"卫生");
    }

    @Override
    public void visit(ParkB parkB) {
        System.out.println("检查"+parkB.getName()+"卫生");
    }
}
