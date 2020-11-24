##  一种Python的创建型/行为型模式



最近遇到一个需求，要求创建对象时，对象的属性可以需要能够动态配置，同时可以灵活的引入新的对象创建需求。我利用metaclass 以及python AOP编程特性来实现了该需求

在正式讲解我的实现方法之前先简单概括一下该模式。

- 该模式应用于资源创建
- 可以根据配置，或者传参决定资源创建策略以及创建对象的类别
- 在创建过程中可以根据需要添加hook函数，通过hook队列来决定hook函数的执行顺序，对创建过程进行控制



#### 设计模式（Design pattern）代表了最佳实践，

本文介绍的这种Python模式，是一种创建型，行为型模式，因此先简要介绍这两种模式。



**创建型模式**

这些设计模式提供了一种创建对象的同时隐藏创建逻辑的方式，而不是直接实例化构造，这使得程序在判断针对某个给定实例需要创建哪些对象时更加灵活

其中和本文有关的创建型模式包括：

- 工厂模式（Factory Pattern）
- 抽象工厂模式（Abstract Factory Pattern）

利用python实现工厂模式时，一般而言，都是基于面向对象的做法，即工厂类，抽象工厂类。



**行为型模式**

这些设计模式关注对象之间的通信

和本文有关的行为型模式包括

- 责任链模式（Chain of Responsibility Pattern）
    - 用于让多个对象处理一个请求时，或者用于预先不知道由哪个对象来处理某种特定的请求时
    - 存在一个对象链(链表，树或者其他便捷的数据结构)
    - 一开始将请求发送给第一个对象，让其处理
    - 对象决定是否处理该请求
    - 重复该过程，直到链尾
- 策略模式（Strategy Pattern）
    - python策略模式的例子如sorted
    - 受一个key函数，该函数实现了一种排序策略

利用python实现行为型模式时，一般来说是采取面向对象的方式实现，在其中穿插一些python特性简化编写和使用。



#### 结构

UML:

![](https://gitee.com/Euraxluo/images/raw/master/picgo/%E6%8D%95%E8%8E%B7.JPG)



#### 该模式整体分为3部分

1. 工厂类，工厂类父类，以及其metaclass
2. hook函数们
3. 调用者相关方法

**第一部分，分为`Factory`类，`FactoryMetaclass`类，`JsonFodder`类，以及Metaclass为每个factory添加的`__yield__`属性**

```python
def __yield__(self, product, format_hook=None, build_hook=None, begin_hook=None, end_hook=None, **kwargs):
    """
    工厂类的__yield__方法
    :param self: resourece 类型对应的工厂类的实例
    :param kwargs: 产品生产函数以及hook函数们需要的参数
    :return: 最终返回工厂生产产品在经过一系列hook函数之后的结果
    """

    """根据product参数，决定我们生产该工厂类的哪个产品"""
    product = product.lower()
    product_func = eval("self.{}".format(product))
    products = product_func(**kwargs)
    """接下来就是hook函数链"""
    """这里还可以继续优化，通过factory的接口设置每一个hook的优先级来运行"""
    if begin_hook:
        products = begin_hook(product, products, **kwargs)

    if format_hook:
        products = format_hook(product, products, **kwargs)

    if build_hook:
        products = build_hook(product, products, **kwargs)

    if end_hook:
        products = end_hook(product, products, **kwargs)

    return products


class FactoryMetaclass(type):
    """
    元类，控制和改变资源工厂类的实例化对象的属性
    1.为每个资源工厂类,添加产品列表属性:__resourceList__
    2.为每个资源工厂类,添加生产函数:__yield__
    """

    def __new__(cls, name, bases, attrs):
        attrs['__resourceList__'] = []
        for k, v in attrs.items():
            if '__' not in k:
                attrs['__resourceList__'].append(k)
        attrs['__yield__'] = __yield__
        return type.__new__(cls, name, bases, attrs)


class Factory(object, metaclass=FactoryMetaclass):
    """
    metaclass被设置为了FactoryMetaclass
    继承该类的子类，就/才 是我们需要的工厂类
    """
    pass

class JsonFodder(Factory):
    """
    从json获取数据源的工厂
    """

    """这里就是我们的产品生产函数"""

    def A(self, file="default——value", **kwargs):
        if kwargs['A_file_path']:
            file = kwargs['A_file_path']
        return yield_item(file)

    def B(self, file="default——value", **kwargs):
        if kwargs['B_file_path']:
            file = kwargs['B_file_path']
        return yield_item(file)

    def C(self, file="default——value", **kwargs):
        if kwargs['C_file_path']:
            file = kwargs['C_file_path']
        return yield_item(file)
    
import json
def yield_item(file):
    with open(file) as json_file:
        json_data = json.load(json_file)
        for j in json_data:
            yield j
```

主要思想就是通过定义父类以及元类，来控制每一个工厂类的创建过程

hook函数通过元类动态添加到工厂类生产函数中



**第二部分，hook函数部分**

```python
def hook_func(product_name, products, **kwargs):
    """某一个hook的写法"""
    for item in products:
        if product_name == "A":
            pass
        yield item
```

hook函数接受产品名，产品生成器，参数列表来做对应的动作



**第三部分，调用部分**

先是不太优雅的面向类名编程🤣🤣🤣

```python
def factory(factory_type):
    """
    传参：工厂类类型，在ETL中可以类比为各种数据源
    返回：工厂类实例
    """
    factory_type = factory_type.capitalize()
    ### 通过遍历资源工厂类的子类，来寻找传参对应的工厂类
    for cls in Factory.__subclasses__():
        if factory_type in cls.__name__:
            return cls()
```

该函数通过工厂类的类名来返回对应的工厂类实例

最终的调用方法

```python
DATA_RESOURCE = {
    'A': 'Json',
    'B': 'Json',
    'C': 'Json',
}
def compent_service(**kwargs):
    for product, factory_type in DATA_RESOURCE.items():
        yield factory(factory_type).__yield__(
            product=product,
            format_hoook=hook_func,
            build_hook=hook_func,
            begin_hook=hook_func,
            end_hook=hook_func,
            # """接下来就是需要传给hook以及产品函数的参数列表"""
            hook_param=kwargs['hook_param'],
            A_file_path=kwargs['A_file_path'],
            B_file_path=kwargs['B_file_path'],
            C_file_path=kwargs['C_file_path'],
        )
```



**优点**：

1. 是基于生成器，整个过程中，当你不调用时，不会产生对象创建
2. 可以使用配置来选择和控制对象的生产过程
3. 调用方法较简单
4. 总体来说，结合了抽象工厂和责任链模式的优点

**缺点**：

1. 调用者必须实现了解工厂的类型有哪些，以及其类名，同时还要很好的了解每个工厂生产的产品，才能很好的进行调用