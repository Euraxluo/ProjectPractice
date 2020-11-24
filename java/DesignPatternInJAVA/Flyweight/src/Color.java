/**
 * 具体享元角色
 */
public class Color {
    private String color;

    public Color(String color) {
        this.color = color;
    }
    public void display(){
        System.out.println(color);
    }
}
