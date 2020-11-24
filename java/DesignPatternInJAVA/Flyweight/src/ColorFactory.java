import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂角色
 */
public class ColorFactory {
    private HashMap<String, Color> pool;

    public ColorFactory() {
        this.pool = new HashMap<String, Color>();
    }
    public Color getColor(String rgb){
        Color color = pool.get(rgb);
        if(color == null){
            color = new Color(rgb);
            pool.put(rgb,color);
        }
        return color;
    }
}
