public class Main {

    public static void main(String[] args) {
        double num =200;

        //第一种使用方式
//        Strategy strategy = new StrategyA();
//        double Num =  strategy.cost(num);

        //第二种使用方法
        Context context = new Context(new StrategyB());
        double Num = context.cost(num);
        System.out.println(Num);
    }
}
