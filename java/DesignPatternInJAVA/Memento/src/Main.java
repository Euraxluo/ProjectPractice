import java.sql.Time;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {
        /**
         * 备份管理者
         */
        CareTaker careTaker = new CareTaker();

        /**
         * 创建备份
         */
        Person person = new Person("name1","男",24);
        careTaker.addMemento(person.createMemento());//创建备份1并添加
        System.out.println(person.toString());//输出当前状态

        try {
            sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //改变对象
        person.setAge(25);
        person.setSex("女");
        person.setName("name2");
        careTaker.addMemento(person.createMemento());//创建备份2并添加
        System.out.println(person.toString());//输出当前状态

        try {
            sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //改变对象
        person.setAge(26);
        person.setSex("中");
        person.setName("name3");
        careTaker.addMemento(person.createMemento());//创建备份3并添加
        System.out.println(person.toString());//输出当前状态

        /**
         * 根据备份的索引来恢复
         */
        person.recovery(careTaker.getMemento(0));
        System.out.println(person.toString());//输出当前状态

        /**
         * 恢复最后一次的状态
         */
        person.recovery(careTaker.LastMemento());
        System.out.println(person.toString());//输出当前状态

        /**
         * 根据备份记录中的时间来恢复
         */
        person.recovery(careTaker.getMemento((String) careTaker.MementoLog().get(1)));
        System.out.println(person.toString());//输出当前状态
    }
}
