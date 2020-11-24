public class StrategyB implements Strategy {
    @Override
    public double cost(double num) {
        //算法B
        if(num >= 200){ //满200-50
            return num -50;
        }
        return num;
    }
}
