public class StrategyA implements Strategy {
    @Override
    public double cost(double num) {
        return num*0.8;//算法A
    }
}
