import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * StringMatch
 * PACKAGE_NAME
 * DoubleArrayTrie
 * 2020/4/12 12:46
 * author:Euraxluo
 * DAT 算法
 * 打算支持序列化，
 * 支持unicode，
 * 支持动态更新树，
 * 支持多模匹配
 */
public class DoubleArrayTrie  implements Serializable {
    /**
     * 杂七杂八的常量
     */
    private static final long serialVersionUID = 7798712407279946750L;
    private static final int DEFAULT_BUFFER_SIZE = 1024*4;
    private static final int EOF=-1;
    /**
     * 匹配失败索引
     */
    public static final int MATCH_FAIL_INDEX=-1;
    /**
     * base arr
     */
    protected int[] baseArr;
    /**
     * check arr
     */
    protected int[] checkArr;

    /**
     * the size of DAT
     */
    protected int size;

    /**
     * 构造函数
     */
    private DoubleArrayTrie() {
    }

    public DoubleArrayTrie(int[] baseArr, int[] checkArr) {
        this.baseArr = baseArr;
        this.checkArr = checkArr;
    }

    public DoubleArrayTrie(int[] baseArr, int[] checkArr, int size) {
        this.baseArr = baseArr;
        this.checkArr = checkArr;
        this.size = size;
    }

    /**
     * 搜索匹配等和算法相关的函数
     */

    /**
     * 按照DAT的状态转移方程进行转移，base[r]+c=s,check[s]=r
     * @param prefixIndex 前缀在DAT中的index
     * @param charValue 转移字符的int值
     * @return 在DAT中的index，不存在为-1
     */
    public int transition(int prefixIndex,int charValue){
        if (prefixIndex<0 || prefixIndex>= size()){
            return MATCH_FAIL_INDEX;
        }
        int index = baseArr[prefixIndex]+charValue;
        if (index>= size() || checkArr[index] != prefixIndex){
            return MATCH_FAIL_INDEX;
        }
        return index;
    }

    /**
     * 匹配字符串
     * @param startIndex DAT开始的index
     * @param str 字符串
     * @return 若匹配上，则为转移后的index的负值，否则返回已匹配上的字符数
     */
    public int match(int startIndex,String str){
        int index = startIndex;
        for (int i=0;i<str.length();i++){
            index = transition(index,str.charAt(i));
            if (index == MATCH_FAIL_INDEX){
                return i;
            }
        }
        return -index;
    }
    /**
     * 判断词是否在DAT中
     * @param word
     * @return
     */
    public boolean isWordMatched(String word){
        return isWordMatched(-match(0,word));
    }


    /**
     * 核心算法
     */
    private static class Builder extends DoubleArrayTrie{


        private static final int INTTIAL_VALUE=-1;
        private static final long serialVersionUID = -3217388949319524099L;
        /**
         * 在使用的base index
         */
        private int usedBaseIndex;

        private Builder(){
            baseArr = new int[]{0};
            checkArr = new int[]{INTTIAL_VALUE};
            size = 1;
            usedBaseIndex = 0;
        }

        private DoubleArrayTrie build(List<String> lexion){
            /**
             * 将字典排序
             */
            lexion.sort(String::compareTo);
            String word,prefix;
            int preIndex;
            for (int i=0;i<lexion.size();i++){
                word = lexion.get(i);
                int matched = match(0,word);
                matched = matched<0?word.length():matched;
                for (int j=matched;j<= word.length();j++){
                    prefix = word.substring(0,j);
                    preIndex = -match(0,prefix);
                    List<Integer> children = genChildren(lexion,i,prefix);
                    insert(preIndex,children,j==word.length());
                }
                matched = -match(0,word);
                baseArr[baseArr[matched]] = i;
            }
            shink();
            return new DoubleArrayTrie(baseArr,checkArr,size);
        }

        /**
         * 例子：
         * 词表：
         * 啊，阿根廷，阿胶，阿拉伯，阿拉伯人，埃及
         * 汉字编码：
         * 啊：1 阿：2 唉：3 根：4 胶：5 拉：6 及：7 廷：8 伯：9 人：10
         * index:1  2  3  4  5  6  7  8  9  10 11 12
         * base :-1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1
         * check:-1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1
         * 汉字 :-1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1
         * 1，插入阿
         * prefixIndex=2，
         *
         *
         *
         *
        /**
         * 插入到Trie
         * @param prefixIndex 前缀对应的index
         * @param children prefixIndex 前缀的后一个字符集合
         * @param isWord
         */
        private void insert(int prefixIndex, List<Integer> children ,boolean isWord){
            int baseId = findBaseIndex(children);
            baseArr[prefixIndex] = baseId;
            if (isWord){
                checkArr[baseId] = prefixIndex;
                usedBaseIndex = baseId+1;
            }
            for (int c : children){
                baseArr[baseId+c] = 0;
                checkArr[baseId+c] = prefixIndex;
            }
        }


        /**
         * 以prefixIndex对应编码的字符为前缀的词，
         * 其prefixIndex对应编码的字符后面的第一个字符的编码为：
         * List<Integer> children ：{c1 c2 c3 cn }
         * 我们需要找到一个baseId，其满足以下情况：
         * {这里base初始化为，check初始化为{-1}，扩容时都设为-1}
         * 1. check[baseId+ci]=-1
         * 2. base[baseId+ci]=-1
         * 核心算法相关的重要函数
         */
        private int findBaseIndex (List<Integer> children){
            int childrenSize = children.size();
            /**
             * 一直循环到找到第一个baseId为止
             */
            for (int baseIdx = usedBaseIndex;;baseIdx++){
                //如果容量不够那么扩充
                if (baseIdx == size()){
                    expand();
                }
                if (childrenSize>0){
                    while (baseIdx+children.get(childrenSize-1) >= size){
                        expand();
                    }
                }
                /**
                 * 确保该处地址未被使用
                 */
                if (checkArr[baseIdx]>=0){
                    continue;
                }
                /**
                 * 将其标识为可使用的
                 */
                boolean isValid = true;
                for (Integer c :children){
                    /**
                     * 需要保证checkArr[baseIdx+ci]跳转到的节点也未被使用
                     * 也即，其所有的children所跳转的node也没有被使用
                     */
                    if (checkArr[baseIdx+c] >= 0){
                        isValid = false;
                        break;
                    }
                }
                if (isValid){
                    return baseIdx;
                }
            }
        }

        /**
         * 给定前缀生成后一个字符的集合
         * @param sortedLexicon
         * @param startLexiconIndex
         * @param prefix
         * @return
         */
        private List<Integer> genChildren(List<String> sortedLexicon,int startLexiconIndex,String prefix){
            List<Integer> children = new LinkedList<>();
            int prefixLen = prefix.length();
            for (int i= startLexiconIndex;i<sortedLexicon.size();i++){
                String word = sortedLexicon.get(i);
                /**
                 * 当词的长度小于前缀或者词的前缀和给定的前缀不一致
                 */
                if (word.length()<prefixLen || !word.substring(0,prefixLen).equals(prefix)){
                    return children;
                }else if (word.length()>prefixLen){
                    int charValue = (int)word.charAt(prefixLen);
//                    System.out.println(children+""+charValue);
                    if (children.isEmpty() || charValue != children.get(children.size()-1)){
                        children.add(charValue);
                    }
                }

            }
            return children;
        }


        /**
         * 工程性代码完善
         */

        /**
         * 容量缩小（假）
         */
        private void  shink(){
            for (int i = checkArr.length -1 ;i>=0;i--){
                if (checkArr[i] == INTTIAL_VALUE){
                    size--;
                }else{
                    break;
                }
            }
        }
        /**
         * 容量扩充
         */
        private void expand(){
            int oldCapacity = size;
            int newCapacity = oldCapacity << 1;
            baseArr = Arrays.copyOf(baseArr,newCapacity);
            Arrays.fill(baseArr,oldCapacity,newCapacity,INTTIAL_VALUE);
            checkArr = Arrays.copyOf(checkArr,newCapacity);
            Arrays.fill(checkArr,oldCapacity,newCapacity,INTTIAL_VALUE);
            size = newCapacity;
        }
    }

    /**
     * 常用的外部调用函数
     */
    /**
     * the size of DAT
     * @return
     */
    public int size(){
        return size;
    }

    /**
     * 通过index获取base数组的值
     * @param index
     * @return
     */
    public int getBaseByIndex(int index){
        indexValid(index);
        return baseArr[index];
    }

    /**
     * 通过index获取base数组的值
     * @param index
     * @return
     */
    public int getCheckByIndex(int index){
        indexValid(index);
        return checkArr[index];
    }

    /**
     * 序列化与反序列化
     */

    /**
     * 序列化
     * @param path
     * @throws IOException
     */
    public void serialize(String path) throws IOException {
        FileChannel channel = new FileOutputStream(path).getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4*(2*size()+1));
        IntBuffer intBuffer = byteBuffer.order(ByteOrder.BIG_ENDIAN ).asIntBuffer();
        intBuffer.put(size());
        intBuffer.put(baseArr);
        intBuffer.put(checkArr);
        channel.write(byteBuffer);
        channel.close();
    }

    /**
     * 加载DAT模型
     * @param path
     * @return
     * @throws IOException
     */
    public static DoubleArrayTrie loadDat(String path) throws IOException {
        return loadDat(new FileInputStream(path));
    }

    /**
     * Make DAT
     * @param path
     * @return
     * @throws FileNotFoundException
     */
    public static DoubleArrayTrie make(String path) throws FileNotFoundException {
        return make(new FileInputStream(path));
    }

    /**
     * 从DAT还原成字典
     * @param dat
     * @return
     */
    public static List<String> restore(DoubleArrayTrie dat){
        String word;
        LinkedList<String> linkedList = new LinkedList<>();
        for (int i=0;i<dat.size();i++){
            if (dat.getCheckByIndex(i) > 0){
                word = restoreWord(dat,i);
                if (dat.isWordMatched(word)){
                    linkedList.add(word);
                }
            }
        }
        return linkedList;
    }

    /**
     * 工具函数
     */
    private boolean isWordMatched(int matchedIndex){
        if (matchedIndex <= 0){
            return false;
        }
        int base = baseArr[matchedIndex];
        return base < size() && checkArr[base] == matchedIndex;
    }

    /**
     * 根据index获取word
     * @param dat
     * @param index
     * @return
     */
    private static String restoreWord(DoubleArrayTrie dat,int index){
        int pre;
        int cur = index;
        StringBuffer sb = new StringBuffer();
        while (cur>0 && cur<dat.size()){
            pre = dat.getCheckByIndex(cur);
            if (pre==cur|| dat.getBaseByIndex(pre) >= cur){
                break;
            }
            sb.insert(0,(char)(cur-dat.getBaseByIndex(pre)));
            cur=pre;
        }
        return sb.toString();
    }
    private static DoubleArrayTrie make(InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        List<String> lexicon = br.lines().map(String::trim).collect(Collectors.toList());
        return make(lexicon);
    }
    private static DoubleArrayTrie make (List<String> lexicon){
        return new Builder().build(lexicon);
    }

    private static DoubleArrayTrie loadDat(InputStream inputStream) throws IOException {
        int[] array;
        array = toIntArray(inputStream);
        int arrlen = array[0];
        int[] baseArr = Arrays.copyOfRange(array,1,arrlen+1);
        int[] checkArr = Arrays.copyOfRange(array,arrlen+1,2*arrlen+1);
        return new DoubleArrayTrie(baseArr,checkArr);
    }

    private static int[] toIntArray(InputStream input) throws IOException {
        byte[] bytes = toByteArray(input);
        IntBuffer intBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
        int[] array = new int[intBuffer.remaining()];
        intBuffer.get(array);
        return array;
    }

    private static byte[] toByteArray(InputStream input) throws IOException {
        try(ByteArrayOutputStream outputStream  = new ByteArrayOutputStream()){
            int n;
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            while (EOF!= (n=input.read(buffer)) ){
                outputStream.write(buffer,0,0);
            }
            return outputStream.toByteArray();
        }
    }

    /**
     * check index valid
     * @param index
     */
    private void indexValid(int index){
        if(index>=size()){
            throw new RuntimeException(String.format("The index {} out of the DAT bound {}",index,size()));
        }
    }
}
