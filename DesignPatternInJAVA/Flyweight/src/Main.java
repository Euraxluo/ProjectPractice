public class Main {

    public static void main(String[] args) {
        ColorFactory factory = new ColorFactory();
        Color c1 = factory.getColor("RED");
        Color c2 = factory.getColor("YELLO");
        Color c3 = factory.getColor("RED");
        Color c4 = new Color("YELLO");
        c1.display();
        c2.display();
        c3.display();
        if(c1 == c3){
            System.out.println("TRUE");
        }else {
            System.out.println("FALSE");
        }
        if(c2 == c4){
            System.out.println("TRUE");
        }else {
            System.out.println("FALSE");
        }

    }
}
