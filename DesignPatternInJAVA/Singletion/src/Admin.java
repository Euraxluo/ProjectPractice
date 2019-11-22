public class Admin {
    //懒汉式，不加同步关键字，线程不安全
    private Admin() {
    }

    private static Admin admin;

    //双重检查
    public static synchronized Admin getAdmin() {
        if (admin == null)//第一次检查，只会运行一次
            synchronized (Admin.class) {//线程安全,在创建这个方法时加锁，只阻塞一次
                if (admin == null)//第二次检查，避免多个线程又创建实例
                    admin = new Admin();
            }
        return admin;
    }

//    public static Admin getAdmin(){//线程不安全
//        if(admin == null) admin = new Admin();
//        return admin;
//    }

    //会影响效率，因为我们只会在第一次admin==null真正的需要做线程安全这个事，避免，两个线程同时创建了两个admin对象
//    public static synchronized Admin getAdmin(){//线程安全,在调用这个方法时，对整个方法加锁
//        if(admin == null) admin = new Admin();
//        return admin;
//    }

    private String name;
    private String ip;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
