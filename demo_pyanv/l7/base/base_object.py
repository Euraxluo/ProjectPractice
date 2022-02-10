# coding:utf8
from typing import Any, Optional, Sequence, Tuple, Union, AnyStr
from enum import Enum

Numeric = Union[int, float]
# JSFunc = Union[str, JsCode]

# class JsCode:
#     def __init__(self, js_code: str):
#         self.js_code = "--x_x--0_0--" + js_code + "--x_x--0_0--"
#
#     def replace(self, pattern: str, repl: str):
#         self.js_code = re.sub(pattern, repl, self.js_code)
#         return self


class BasicOpts:
    __slots__: ("options",)

    def update(self, **kwargs):
        self.options.update(kwargs)

    def get(self, key: str=None) -> Any:
        if not key:
            return self.options
        return self.options.get(key)


class BackgroundOptions(BasicOpts):
    def __init__(self,
                 color: Optional[str] = "#fffbe6",
                 image: Optional[str] = "undefined",
                 position: Optional[str] = "center",
                 size: Optional[str] = "auto auto",
                 repeat: Optional[str] = "no-repeat",
                 opacity: Optional[Numeric] = 1,
                 quality: Optional[Numeric] = 1,
                 angle: Optional[Numeric] = 20
                 ):
        self.options: dict = {
            "color": color,
            "image": image,
            "position": position,
            "size": size,
            "repeat": repeat,
            "opacity": opacity,
            "quality": quality,
            "angle": angle,
        }

class GridOptions(BasicOpts):
    def __init__(self,
                 type: Optional[str] = "dot",
                 size: Optional[Numeric] = 10,
                 visible: Optional[bool] = True,
                 color: Optional[str] = "#AAAAAA",
                 thickness: Optional[Numeric] = 1.0,
                 ):
        self.options: dict = {
            "type": type,
            "size": size,
            "visible": visible,
            "args":{
                "thickness": thickness,
                "color": color,
            },
        }

if __name__ == '__main__':
    x  = BackgroundOptions()
    print(x.get())

    x  = GridOptions()
    print(x.get())