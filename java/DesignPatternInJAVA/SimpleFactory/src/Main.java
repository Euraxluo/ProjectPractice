public class Main {
    public static void main(String[] args){
        try {
//            Fruit apple = FruitFactory.getApple();
//            Fruit banana = FruitFactory.getBanana()
            Fruit apple = FruitFactory.getFruit("Apple");
            Fruit banana = FruitFactory.getFruit("Banana");
            apple.get();
            banana.get();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
