import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        User user1 = User.getUser();
        User user2 = User.getUser();
        user1.setName("user1");
        user2.setName("user2");
        System.out.println(user1.getName() + "\n");
        System.out.println(user2.getName() + "\n");
        Admin admin1 = Admin.getAdmin();
        Admin admin2 = Admin.getAdmin();
        admin1.setName("Admin1");
        admin2.setName("Admin2");
        System.out.println(admin1.getName() + "\n");
        System.out.println(admin2.getName() + "\n");
    }
}
