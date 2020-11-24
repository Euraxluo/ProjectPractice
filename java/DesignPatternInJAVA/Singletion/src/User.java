public class User {
    // 线程安全，但是第一次运行时比较耗费资源
    // 单例模式，一般用于比较大，复杂的对象，只初始化一次
    // 饿汉式,在第一次加载这个类时，就会定义这个引用对象，不可改变
    public static final User user = new User();//这里创建只有一个固定引用的user对象

    private User() {//构造函数应该是private，使得外部不能new对象，只能通过getInstance方法得到这个类的对象
    }

    public static User getUser() {//整个函数只能通过这个函数获得类的实例
        return user;//这里返回
    }


    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
