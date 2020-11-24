import java.util.List;

/**
 * 统一的接口
 */
public interface IFile {
    public void display();//显示
    public boolean add(IFile file);//添加
    public boolean remove(IFile file);//移除
    public List<IFile> getChild();//子文件
}
