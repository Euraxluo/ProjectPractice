# coding:utf8
from typing import Any, Optional, Sequence, Tuple, Union, AnyStr, Mapping
import warnings

Numeric = Union[int, float]
MoreOption = Optional[Mapping]


class BasicOptsMetaClass(type):

    def __init__(self, cls, inherit, attributes):
        if len(inherit) == 0 or (cls != 'BasicOpts' and BasicOpts not in inherit):
            warnings.warn(f"{cls} Must inherit the BasicOpts!")

    def __call__(self, *args, **kwargs):
        if args:
            warnings.warn(f"must use keyword argument for OptionCls")
        obj = object.__new__(self)
        obj.__dict__['options'] = {}
        for k, v in kwargs.items():
            """
            Some keyword and python conflict, you can use the beginning of the underline to solve, but we will warn
            """
            if k[0] == '_':
                warnings.warn(f"argument best not to start with an underline,because we will remove the underline")
                k = k[1:]
            if v.__class__ in BasicOpts.__subclasses__():
                obj.__dict__['options'][k] = v.getOpt()
                continue
            obj.__dict__['options'][k] = v
        self.__init__(obj, *args, **kwargs)
        return obj


class BasicOpts(object, metaclass=BasicOptsMetaClass):
    __slots__: ("options",)

    def getOpt(self, key: str = None) -> Any:
        if not key:
            return self.options
        return self.options.get(key)

    def setOpt(self, *args, **kwargs):
        """
        这里会进行opt类参数默认值的填充
        :param args: 必须传入当前环境的locals()
        :param kwargs:
        :return:
        """
        if len(args) == 1:
            for k, v in args[0].items():
                if self == v or k == 'kwargs' or self.__class__ == v:
                    continue
                if k[0] == '_':
                    warnings.warn(
                        f"argument best not to start with an underline,because we will remove the underline")
                    k = k[1:]
                if v.__class__ in BasicOpts.__subclasses__():
                    self.options[k] = v.getOpt()
                    continue
                self.options[k] = v
        elif kwargs:
            for k, v in kwargs.items():
                """
                Some keyword and python conflict, you can use the beginning of the underline to solve, but we will warn
                """
                if k[0] == '_':
                    warnings.warn(f"argument best not to start with an underline,because we will remove the underline")
                    k = k[1:]
                if v.__class__ in BasicOpts.__subclasses__():
                    self.options.get(k).update(v.getOpt())
                    continue
                self.options.update(**{k: v})


class BackgroundOptions(BasicOpts):
    def __init__(self,
                 color: Optional[AnyStr] = "#fffbe6",
                 image: Optional[AnyStr] = "undefined",
                 position: Optional[AnyStr] = "center",
                 size: Optional[AnyStr] = "auto auto",
                 repeat: Optional[AnyStr] = "no-repeat",
                 opacity: Optional[Numeric] = 1,
                 quality: Optional[Numeric] = 1,
                 angle: Optional[Numeric] = 20,
                 **kwargs
                 ):
        self.setOpt(locals())


class Args(BasicOpts):
    def __init__(self, **kwargs):
        self.setOpt(locals())


class GridOptions(BasicOpts):
    def __init__(self,
                 type: Optional[AnyStr] = "dot",
                 size: Optional[Numeric] = 10,
                 visible: Optional[bool] = True,
                 color: Optional[AnyStr] = "#AAAAAA",
                 thickness: Optional[Numeric] = 1.0,
                 **kwargs
                 ):
        arg = Args(thickness=thickness, color=color)
        self.setOpt(locals())


class SnaplineOptions(BasicOpts):
    def __init__(self,
                 enabled: Optional[bool] = False,
                 className: Optional[AnyStr] = "snapline",
                 tolerance: Optional[Numeric] = 10,
                 sharp: Optional[bool] = False,
                 resizing: Optional[bool] = False,
                 clean: Union[Numeric, bool] = False,
                 **kwargs
                 ):
        self.setOpt(locals())


class ScrollerOptions(BasicOpts):
    def __init__(self,
                 enabled: Optional[bool] = False,
                 pannable: Optional[bool] = False,
                 # className: Optional[AnyStr] = "scroller",
                 # width: Optional[Numeric] = None,
                 # height: Optional[Numeric] = None,
                 # modifiers: Union[AnyStr, Sequence, None] = 'alt',
                 # cursor: Optional[AnyStr] = None,
                 # padding: Union[Numeric, Mapping,None] = None,
                 minVisibleWidth: Optional[Numeric] = 10,
                 minVisibleHeight: Optional[Numeric] = 10,
                 pageVisible: Optional[bool] = False,
                 pageBreak: Optional[bool] = False,
                 # pageWidth: Optional[Numeric] = None,
                 # pageHeight: Optional[Numeric] = None,
                 # autoResize: Optional[bool] = True,
                 # background: Union[BackgroundOptions, bool, None] = False,
                 **kwargs
                 ):
        self.setOpt(locals())


if __name__ == '__main__':
    x = BackgroundOptions()
    print(x.getOpt())

    x = GridOptions()
    print(x.getOpt())

    x.setOpt(arg=Args(thickness=2))
    print(x.getOpt())

    y = SnaplineOptions(className='1', _sync=x)
    print(y.getOpt())

    y.setOpt(className='2')
    print(y.getOpt())
