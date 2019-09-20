import java.util.List;

public class Main {

    public static void main(String[] args) {
        Folder root = new Folder("/");
        Folder home = new Folder("home");
        File f1 = new File("test.txt");
        File f2 = new File(".vimrc");
        root.add(home);
        root.add(f1);
        home.add(f2);
        displayTree(root,0);
    }

    private static void displayTree(IFile folder, int deep) {
        for (int i=0 ;i<deep;i++){
            System.out.print("  ");
        }
        folder.display(); //显示本节点
        List<IFile> children = folder.getChild();//获取子树
        for (IFile file:children) {
            if(file instanceof File){//如果是Fil,直接输出
                for (int i=0;i<=deep;i++){
                    System.out.print("  ");
                }
                file.display();
            }else {
                displayTree(file,deep+1);
            }
        }
    }
}
