public abstract class  CarDecorator implements Car {
    // 抽象装饰角色
    // 首先需要和抽象组件角色接口一致,因此需要implements 抽象组件
    // 其次需要作为抽象角色,需要包含组建的引用
    // 又需要为具体装饰角色定义与提供一些与组件有关的接口

    private Car car;//组件的引用

    //操作(装饰)组件的接口(构造函数)
    public CarDecorator(Car car) {
        this.car = car;
    }

    //获取组件引用的接口
    public Car getCar() {
        return car;
    }

    @Override
    public abstract void show();
}
