public class VisitorB implements Visitor {
    @Override
    public void visit(Park park) {

    }

    @Override
    public void visit(ParkA parkA) {

    }

    @Override
    public void visit(ParkB parkB) {
        System.out.println("清洁工打扫:"+parkB.getName());
    }
}
