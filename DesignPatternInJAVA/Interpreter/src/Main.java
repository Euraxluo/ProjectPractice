public class Main {
    /**
     * 创建规则
     * @return
     */
    //规则:A和B是C
    public static Expression CExpression(){
        Expression A = new TerminalExpression("A");
        Expression B = new TerminalExpression("B");
        return new OrExpression(A,B);
    }
    //规则:D属于E
    public static Expression EExpression(){
        Expression D = new TerminalExpression("D");
        Expression E = new TerminalExpression("E");
        return new AndExperssion(D,E);
    }
    public static void main(String[] args) {
        Expression Ce = CExpression();
        Expression Ee = EExpression();
        System.out.println("A is C?"+ Ce.interpret("A"));
        System.out.println("D is E?"+ Ee.interpret("E D"));//这里换成E或者D都返回false
    }
}
     