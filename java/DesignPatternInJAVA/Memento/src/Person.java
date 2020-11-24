public class Person {
    private String name;
    private String sex;
    private int age;

    public Person(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    /**
     * 创建备份
     * @return
     */
    public Memento createMemento(){
        return new Memento(name,sex,age);
    }

    /**
     * 从备份恢复
     * @param memento
     */
    public void recovery(Memento memento){
        System.out.println("recovery memento:"+memento.toString());
        this.age = memento.getAge();
        this.name = memento.getName();
        this.sex = memento.getSex();
    }
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
