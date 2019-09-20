public class VisitorA implements Visitor {
    @Override
    public void visit(Park park) {

    }

    @Override
    public void visit(ParkA parkA) {
        System.out.println("清洁工打扫:"+parkA.getName());
    }

    @Override
    public void visit(ParkB parkB) {

    }
}
