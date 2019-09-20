import java.util.Date;

/**
 * 具体的中介类
 */
public class ChatRoom {
    public static void showMessage(User user,String message){
        System.out.println(new Date().toString()
        +"["+user.getName()+"]:"+message);
    }
}
