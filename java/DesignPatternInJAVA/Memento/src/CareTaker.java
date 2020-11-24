import java.text.SimpleDateFormat;
import java.util.*;

public class CareTaker {
    private IndexLinkedHashMap<String,Memento> mementoMap = new IndexLinkedHashMap<String,Memento>();

    /**
     * 根据当前时间进行备份
     * @param memento
     */
    public void addMemento(Memento memento){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");//设置日期格式
        String now = df.format(new Date());// new Date()为获取当前系统时间
        mementoMap.put(now,memento);
    }

    /**
     * 根据时间获取备份
     * @param date
     * @return
     */
    public Memento getMemento(String date){
        return (Memento) mementoMap.get(date);
    }

    /**
     * 根据索引获取备份
     * @param index
     * @return
     */
    public Memento getMemento(int index){
        return (Memento) mementoMap.get(index);
    }

    /**
     * 获取备份日志
     * @return
     */
    public Map<Integer,Object> MementoLog(){
        return mementoMap.getIndex();
    }

    /**
     * 返回最后一次的备份
     * @return
     */
    public Memento LastMemento(){
        return (Memento) mementoMap.get(MementoLog().get(mementoMap.IndexSize()-1));
    }

    /**
     * 内部类,可以抽取为公共类,在LinkedHashMap的基础上增加通过存储索引获得数据的方式
     * @param <K>
     * @param <V>
     */
    public class IndexLinkedHashMap<K,V> extends LinkedHashMap{
        private Map<Integer,Object> index = new HashMap<Integer,Object>();
        private int cur = 0;
        public Object put(Object key, Object val){
            super.put(key,val);
            return index.put(cur++, key);
        }
        public Object get(int i){
            if(i>=cur){
                i = cur-1;
            }
            Object key = index.get(i);
            return super.get(key);
        }
        public Object get(Object key){
            return super.get(key);
        }

        /**
         * 返回整个map的大小
         * @return
         */
        public int IndexSize(){
            return this.cur;
        }

        /**
         * 返回index:存储<索引,K>的map
         * @return
         */
        public Map<Integer,Object> getIndex(){
            return this.index;
        }
    }
}
