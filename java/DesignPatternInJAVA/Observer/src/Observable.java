import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者基类
 */
public class Observable {
    //观察者列表
    private List<Observer> observers = new ArrayList<Observer>();
    private boolean changed = false;//状态标志

    public boolean getChanged() {
        return changed;
    }

    public void setChanged() {
        this.changed = true;
    }

    //将观察者添加到观察者列表
    public void addObserver(Observer observer){
        if(observer == null)
            throw new NullPointerException();
        observers.add(observer);
    }

    //当外界调用此函数时,回去调用回调函数update
    public void notifyObservers() {
        notifyObservers(null);
    }

    //当外界调用此函数时,回去调用回调函数update
    public void notifyObservers(Object arg){
        if(!changed)
            return;
        clearChanged();//清除状态
        for (Observer o:observers) {
            o.update(this,arg);
        }
    }
    //重置状态
    private void clearChanged() {
        changed = false;
    }
    public int countObservers(){
        return observers.size();
    }
}
