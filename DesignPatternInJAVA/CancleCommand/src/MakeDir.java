import java.io.File;

/**
 * 命令接收者
 * 包含两个操作:创建目录,删除目录
 */
public class MakeDir {
    public boolean createDir(String name) {
        File dir = new File(name);
        if (dir.exists()) {
            System.out.println("目录" + name + "创建失败,目标目录已存在");
            return false;
        } else {
            if (dir.mkdirs()) {
                System.out.println("创建目录" + name + "成功");
                return true;
            } else {
                System.out.println("创建目录" + name + "失败");
                return false;
            }
        }
    }

    public void deleteDir(String name) {
        File dir = new File(name);
        if (dir.exists()) {
            if (dir.delete()) {
                System.out.println("删除目录" + name + "成功");
            } else {
                System.out.println("删除目录" + name + "失败");
            }
        }else {
            System.out.println("删除目录"+name+"失败,目标目录不存在");
        }
    }
}
