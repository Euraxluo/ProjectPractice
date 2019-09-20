public class Main {

    public static void main(String[] args) {
        House house = new House();
        //1. make self
//        house.setFloor("自己做的地板");
//        house.setWall("自己做的墙");
//        house.setHouseTop("自己做的屋顶");

        //2. self find builder to make
//        HouseBuilder builder = new NO1_Builder();
//        builder.makeFloor();
//        builder.makeWall();
//        builder.makeHouseTop();
//        house =  builder.getHouse();

        //3. find Director to make
        HouseBuilder builder = new NO2_Builder();
        HouseDirector director = new HouseDirector();
        director.makeHouse(builder);
        house = builder.getHouse();


        System.out.println(house.getFloor());
        System.out.println(house.getHouseTop());
        System.out.println(house.getWall());
    }
}
