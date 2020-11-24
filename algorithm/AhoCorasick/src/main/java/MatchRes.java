/**
 * StringMatch
 * PACKAGE_NAME
 * MatchRes
 * 2020/4/11 17:37
 * author:Euraxluo
 * 单个模式匹配结果
 */
public class MatchRes {
    /**
     * 匹配到的结果
     */
    private final String word;

    private final int start;
    private final int end;

    public MatchRes(String word, int start, int end) {
        this.word = word;
        this.start = start;
        this.end = end;
    }
    public String getWord(){
        return this.word;
    }
    public int getStart(){
        return this.start;
    }
    public int getEnd(){
        return this.end;
    }
    @Override
    public String toString(){
        return this.start+":"+this.end+"="+this.word;
    }
}
