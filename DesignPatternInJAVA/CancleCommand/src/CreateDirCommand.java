import java.util.ArrayList;

/**
 * 具体的命令角色
 */
public class CreateDirCommand implements Command {
    private ArrayList<String> dirNameList;
    private MakeDir makeDir;

    public CreateDirCommand(MakeDir makeDir) {
        this.dirNameList = new ArrayList<String>();
        this.makeDir = makeDir;
    }

    @Override
    public void execute(String dirName) {
        if(makeDir.createDir(dirName)){
            dirNameList.add(dirName);
        }
    }

    @Override
    public void undo() {
        if(dirNameList.size()>0){
            makeDir.deleteDir(dirNameList.get(dirNameList.size()-1));
            dirNameList.remove(dirNameList.size()-1);
        }else {
            System.out.println("没有需要撤销的操作");
        }
    }
}
