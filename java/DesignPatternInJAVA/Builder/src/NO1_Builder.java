public class NO1_Builder implements HouseBuilder {

    private House house = new House();

    @Override
    public void makeFloor() {
        house.setFloor("NO1 build floor");
    }

    @Override
    public void makeWall() {
        house.setWall("NO1 build wall");
    }

    @Override
    public void makeHouseTop() {
        house.setHouseTop("NO1 build HouseTop");
    }

    @Override
    public House getHouse() {
        return house;
    }
}
