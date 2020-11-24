public class NO2_Builder implements HouseBuilder {
    private House house = new House();

    @Override
    public void makeFloor() {
        house.setFloor("NO2 build floor");
    }

    @Override
    public void makeWall() {
        house.setWall("NO2 build wall");
    }

    @Override
    public void makeHouseTop() {
        house.setHouseTop("NO2 build houseTOP");
    }

    @Override
    public House getHouse() {
        return house;
    }
}
