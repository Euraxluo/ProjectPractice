public class Main {

    public static void main(String[] args) {
        /**
         * 基于继承实现的Adapter,在更改接口时,其实不是很方便
         */
        AdapterA adapterA = new AdapterA();
        adapterA.out20V();

        /**
         * 基于委让实现的Adapter,切换外部接口,十分方便
         */
//        AdapterB adapterB = new AdapterB(new Socket220V());
//        adapterB.out18V();
        AdapterB adapterB = new AdapterB(new Socket110V());
        adapterB.out18V();
    }
}
