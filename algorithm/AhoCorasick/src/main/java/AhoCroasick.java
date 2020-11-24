import java.util.*;

/**
 * StringMatch
 * PACKAGE_NAME
 * AhoCroasick
 * 2020/4/11 13:19
 * author:Euraxluo
 * AhoCroasick Bell lab version
 */
public class  AhoCroasick {
    private State root = new State(0);
    private boolean isIgnoreCase = false;
    private boolean isBuildFail = false;

    public AhoCroasick() {
        root.setFail(root);
    }

    /**
     * 配置函数
     * @return
     */
    public AhoCroasick IgnoreCase(){
        this.isIgnoreCase = true;
        return this;
    }


    /**
     * Goto函数
     * @param word
     */
    public void insert(String word) {
        State curNode = root; //每次都是从root节点开始
        for (Character c : word.toCharArray()){
            curNode = curNode.addState(c);
        }
        curNode.addOutput(word);
    }

    /**
     * Fail函数
     * 先BFS，然后对每个节点设置其fail指针
     */
    public void buildFail(){
        Queue<State> queue = new LinkedList<State>();
        State node = root;
        /**
         * 先把root的fail指向root，然后把第一层的fail指向root
         */
        for (State rootTargetState:this.root.getStates()){
            rootTargetState.setFail(this.root);
            queue.offer(rootTargetState); //添加到队列
        }
        /**
         * 然后之后的几层通过bfs,将当前state的fail指向father的faile的child
         */
        while (!queue.isEmpty()){
            State curState = queue.poll();//取出并删除第一个元素
            /**
             * 遍历其状态转移条件
             */
            for (Character shift:curState.getShifts()){
                /**
                 * 理解为子节点
                 */
                State targetState = curState.nextState(shift);
                queue.offer(targetState);//将子节点添加到对队列中
                /**
                 * 当前状态的失败指针
                 */
                State shiftFailState = curState.fail() == null?this.root:curState.fail();
                /**
                 * 回退到某个状态
                 */
                while (shiftFailState.nextState(shift) == null){
                    shiftFailState = shiftFailState.fail();
                    if (shiftFailState.Depth()==0) break;
                }
                if (shiftFailState.containsState(shift)){
                    /**
                     * 目标节点的fail会指向当前节点的失败last Faile指针的目标状态
                     */
                    State FailState = shiftFailState.nextState(shift);
                    targetState.setFail(FailState);
                    /**
                     * 这里会添加一系列的output
                     */
                    targetState.addOutput(FailState.output());
                }else {
                    targetState.setFail(this.root);
                }
            }
        }
        this.isBuildFail=true;
    }

    /**
     * OutPut函数
     */
    public Collection<MatchRes> outPut(String text){
        checkBuildFail();
        /**
         * 初始状态为root/0
         */
        List<MatchRes> ouputs = new ArrayList<MatchRes>();
        State curState = this.root;
        int position = 0;
        for (Character shift : text.toCharArray()){
            if (isIgnoreCase){
                shift = Character.toLowerCase(shift);
            }
            curState = shiftState(curState,shift);
            Collection<String> outputs = curState.output();
            int finalPosition = position;
            outputs.forEach(match->ouputs.add(new MatchRes(match, finalPosition -match.length()+1, finalPosition)));
            position++;
        }
        return ouputs;
    }

    /**
     * 工具函数
     * 检查是否构建好fail指针
     * 主要是方便调用
     */
    private void checkBuildFail() {
        if (!this.isBuildFail){
            buildFail();
        }
    }

    /**
     * output函数关键的状态转移算法
     * @param curState
     * @param shift
     * @return
     */
    private State shiftState(State curState, Character shift){
        /**
         * 先按照goto表转移
         */
        State shiftState = curState.nextState(shift);

        /**
         * 跳转失败的话，按照fail表转移
         */
        while (shiftState == null){
            curState = curState.fail();
            shiftState = curState.containsState(shift)?curState.nextState(shift):this.root;
        }
        return shiftState;
    }


    /**
     * 外部封装函数
     */
    public void addKeyword(String word){
        insert(word);
    }
    public Collection<MatchRes> parseText(String text){
        return outPut(text);
    }

}
