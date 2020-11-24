public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }
    public double cost(double num){
        //也可以设置优先策略
        if(strategy != null){
            return this.strategy.cost(num);
        }else {
            System.out.println("没有策略");
            return  -1;
        }

    }
}
