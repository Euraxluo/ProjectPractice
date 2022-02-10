#coding:utf8
class Markup :
    """
    tagName: string SVG/HTML 元素标签名。
    ns?: string 与 tagName 对应的元素命名空间，默认使用 SVG 元素命名空间 "http://www.w3.org/2000/svg"，当 tagName 指定的标签是 HTML 元素时，需要使用 HTML 元素的命名空间 "http://www.w3.org/1999/xhtml"。
    selector?: string SVG/HTML 元素的唯一标识，通过该唯一标识为该元素指定属性样式。例如，为 Shape.Rect 节点指定 <rect> 和 <text> 元素的属性样式
    groupSelector?: string | string[] 群组选择器，通过群组选择器可以为该群组对应的多个元素指定样式。
    attrs?: { [key: string]: string | number } 该 SVG/HTML 元素的默认属性键值对，通常用于定义那些不变的通用属性，这些默认样式也可以在实例化节点时被覆盖。 ，markup 的 attrs 属性只支持原生的 SVG 属性，也就是说 X6 的自定义属性在这里不可用。
    style?: { [key: string]: string | number } 该 SVG/HTML 元素的行内样式键值对。
    className?: string | string[] 该 SVG/HTML 元素的 CSS 样式名。
    textContent?: string textContent 该 SVG/HTML 元素的文本内容
    children?: Markup[] 嵌套的子元素。
    """
    def __init__(self,ns,selector,groupSelector,style,className,textContent,children):
        self._ns = ns
        self._selector = selector
        self._groupSelector = groupSelector
        self._style = style
        self._className = className
        self._textContent = textContent
        self._children = children
