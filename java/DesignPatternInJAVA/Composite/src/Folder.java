import java.util.ArrayList;
import java.util.List;

public class Folder  implements IFile{
    private String name;
    private List<IFile> children;

    public Folder(String name) {
        this.name = name;
        children = new ArrayList<IFile>();
    }

    @Override
    public void display() {
        System.out.println(name);
    }

    @Override
    public boolean add(IFile file) {
        return children.add(file);
    }

    @Override
    public boolean remove(IFile file) {
        return children.remove(file);
    }

    @Override
    public List<IFile> getChild() {
        return children;
    }
}
