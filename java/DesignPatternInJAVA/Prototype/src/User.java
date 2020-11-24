import java.util.ArrayList;
import java.util.List;

public class User implements Cloneable {
    private String name;//具体的数值
    private int age;//具体的数值
    private String School;//具体的数值
    private List<String> Courses;//一个引用

    public List<String> getCourses() {
        return Courses;
    }

    public void setCourses(List<String> courses) {
        Courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }
    public User clone(){
        //浅拷贝
//        try {
//            User user = (User) super.clone();
//            return user;
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//            return null;
//        }
        //深拷贝
        try {
            User newUser = (User) super.clone();
            List<String> newCourse = new ArrayList<>();
            for(String course:this.getCourses()){
                newCourse.add(course);
            }
            newUser.setCourses(newCourse);
            return newUser;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
