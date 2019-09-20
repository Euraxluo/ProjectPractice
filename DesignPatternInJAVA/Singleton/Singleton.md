#单例模式   
单例模式是一种对象创建型模式，使用单例模式，可以保证为一个类只生成唯一的实例对象。
保证一个类、只有一个实例存在，同时提供能对该实例加以访问的全局访问方法。 
一般用于比较大，复杂的对象，只初始化一次。

#创建方式
1.饿汉式。
在第一次加载这个类时，就会定义这个引用对象，不可改变
线程安全，但是第一次运行时比较耗费资源

```java
    public static final User user = new User();//这里创建只有一个固定引用的user对象

    private User() {//构造函数应该是private，使得外部不能new对象，只能通过getInstance方法得到这个类的对象
    }

    public static User getUser() {//整个函数只能通过这个函数获得类的实例
        return user;//这里返回
    }
```

2.懒汉式。
如果不加同步关键字，线程不安全
```java
    //懒汉式，
    private Admin() {}

    private static Admin admin;

    public static Admin getAdmin(){//线程不安全
             if(admin == null) admin = new Admin();
             return admin;
         }
```
3.双重检查。
双重检查，是对懒汉式的改进。
```java
    private Admin() {}

    private volatile static Admin admin;//这里加了可见性关键字,下面的方法不用加锁
    public static Admin getAdmin() {
        if (admin == null)//第一次检查，只会运行一次
            synchronized (Admin.class) {//线程安全,在创建这个方法时加锁，只阻塞一次
                if (admin == null)//第二次检查，避免多个线程又创建实例
                    admin = new Admin();
            }
        return admin;
    }

```
```java
    private Admin() {}

    private static Admin admin;//没有可见性
    public static synchronized Admin getAdmin() {//需要加锁
        if (admin == null)//第一次检查，只会运行一次
            synchronized (Admin.class) {//线程安全,在创建这个方法时加锁，只阻塞一次
                if (admin == null)//第二次检查，避免多个线程又创建实例
                    admin = new Admin();
            }
        return admin;
    }

```
#应用
在应用系统开发中，我们常常有以下需求：
- 在多个线程之间，比如servlet环境，共享同一个资源或者操作同一个对象
- 在整个程序空间使用全局变量，共享资源
- 大规模系统中，为了性能的考虑，需要节省对象的创建时间等等。

因为Singleton模式可以保证为一个类只生成唯一的实例对象，
所以这些情况，Singleton模式就派上用场了。
