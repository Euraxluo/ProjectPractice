## 一种Python自定义异常的实现模式



实际开发中，有时候系统提供的异常类型不能满足开发的需求。这时候你可以通过创建一个新的异常类来拥有自己的异常。异常类继承自 Exception 类，可以直接继承，或者间接继承。

其实自定义异常很简单，但是当我们拥有数以百计的异常时，如何良好的管理，成了一个问题。这里给出一种实现方法。

#### 整体结构分为3部分

- base基类，该类继承了Exception，同时在构造函数中为每一个异常类创建了属于自己的code属性以及msg属性
- define文件，该文件包括两个类（类的数量由异常类的属性个数决定）
- 自定义异常类，以及其扩展类

**直接po代码**

1. 基类base_error

```python
from error_define import ErrorCode,ErrorMsg
class BaseError(Exception):
    """
    自定义异常基类
    """
    def __init__(self):
        self.code = ErrorCode[self.__class__.__name__].value
        self.msg = ErrorMsg[self.__class__.__name__].value
```

2. error_define

```python
from enum import Enum,unique
@unique
class ErrorCode(Enum):
    """
    异常的code定义
    """
    BaseError = 1000
    EnvError = 1200
    FeishuError = 1201

@unique
class ErrorMsg(Enum):
    """
    异常的msg定义
    """
    BaseError = 'Base Error'
    EnvError = 'Environment Error'
    FeishuError = 'feishu retry times out,plz check feishu configs'
```

3. 自定义异常类

```python
from base_error import BaseError

class EnvError(BaseError):
    """
    环境异常基类
    一般需要重启环境，或者人工干预
    """
    pass

class FeishuError(EnvError):
    """
    飞书环境异常
    """
    pass
```