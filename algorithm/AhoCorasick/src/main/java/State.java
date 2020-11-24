import java.util.*;

/**
 * StringMatch
 * AC
 * State
 * 2020/4/11 14:24
 * author:Euraxluo
 * 状态定义
 */
public class State {
    /**
     * 节点深度，也就是节点上的数字
     */
    private int depth;
    /**
     * goto表 ,一般来说这个部分不同大的实现的改变会改变各种性能
     * 这里使用TreeMap，log（n）
     * 也有使用hashMap之内的
     * 其实就是某个节点那些指向子节点的线
     */
    private Map<Character,State> success = new TreeMap<Character, State>();
    /**
     *
     * 一般而言是一个指向与匹配串相同后缀所在状态的虚线
     */
    private State fail = null;
    /**
     * output表，这里不同实现可以让我们丰富我们的匹配结果
     */
    private Set<String> output=null;

    /**
     * 当depth==0时，他就是根节点（虚节点）
     * @param depth
     */
    public State(int depth) {
        this.depth = depth;
    }
    public int Depth(){
        return this.depth;
    }
    /**
     * 添加一个匹配到的模式串
     * @param word
     */
    public void addOutput(String word){
        if (this.output == null){
            this.output = new TreeSet<String>();
        }
        this.output.add(word);
    }

    /**
     * 添加一些匹配到的模式串
     * @param words
     */
    public void addOutput(Collection<String> words){
        for (String word : words){
            addOutput(word);
        }
    }

    /**
     * 获取这个节点的output
     * @return
     */
    public Collection<String> output(){
        return this.output == null? Collections.<String> emptyList() : this.output;
    }

    /**
     * 获取这个节点的fail状态
     * @return
     */
    public State fail(){
        return this.fail;
    }

    /**
     * 设置节点的fail状态
     * @param failState
     */
    public void setFail(State failState){
        this.fail = failState;
    }


    /**
     * 获取当前状态的下一个状态
     * @param c
     * @return
     */
    public State nextState(Character c){
        State nextState = this.success.get(c);
        return nextState;
    }

    /**
     * 判断是否有下一个状态
     * @param c
     * @return
     */
    public boolean containsState(Character c){
        return this.success.containsKey(c);
    }

    /**
     * 如果此状态存在，那么直接返回
     * 如果此在状态不存在，那么先new一个，再返回
     * @return
     */
    public State addState(Character c){
        State nextState = nextState(c);
        if (nextState == null){
            nextState = new State(this.depth+1);
            this.success.put(c,nextState);
        }
        return nextState;
    }

    /**
     * 获取当前节点全部的转移状态
     * @return
     */
    public Collection<State> getStates(){
        return this.success.values();
    }

    /**
     * 获取当前节点的所有的状态转移
     * @return
     */
    public Collection<Character> getShifts(){
        return this.success.keySet();
    }



}
