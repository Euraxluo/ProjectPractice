import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setName("user1");
        user.setAge(12);
        user.setSchool("西南科技大学");

        List<String> Courses = new ArrayList<>(Arrays.asList("cs219", "cs109"));
        user.setCourses(Courses);

        //获取原型克隆
        User user2 = user.clone();
        user2.setName("user2");

        //更新user1的Course
        Courses.add("cs231n");
        user.setCourses(Courses);


        System.out.println(user.getName() + user.getCourses());
        System.out.println(user2.getName() + user2.getCourses());

    }
}
