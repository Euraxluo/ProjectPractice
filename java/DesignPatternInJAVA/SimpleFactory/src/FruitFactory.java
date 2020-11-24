
public class FruitFactory {
    //1.为每个产品设置一个产品线
    public static Fruit getApple(){
        return new Apple();
    }
    public static Fruit getBanana(){
        return new Banana();
    }

    //2.设置一个产品线,可以根据用户的需求生产实例类对象
    public static Fruit getFruit(String type) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //1.手动判断type来返回实例类对象
//        if(type.equalsIgnoreCase("apple")){
//            return Apple.class.newInstance();
//        }else if(type.equalsIgnoreCase("banana")){
//            return Banana.class.newInstance();
//        }else{
//            return null;
//        }
        //2.使用forName根据type来获取类对象
        Class fruit = Class.forName(type);
        return (Fruit) fruit.newInstance();
    }
}
