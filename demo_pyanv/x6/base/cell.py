#coding:utf8
# import folium
class Cell:
    """
    选项名	类型	默认值	描述
    id	String	undefined	节点/边的唯一标识，默认使用自动生成的 UUID。
    markup	Markup	undefined	节点/边的 SVG/HTML 片段。
    attrs	Object	{ }	节点/边属性样式。 attrs 选项是一个复杂对象，该对象的 Key 是节点中 SVG 元素的选择器(Selector)，对应的值是应用到该 SVG 元素的 SVG 属性值(如 fill 和 stroke)
    shape	String	undefined	渲染节点/边的图形。
    view	String	undefined	渲染节点/边的视图。
    zIndex	Number	undefined	节点/边在画布中的层级，默认根据节点/边添加顺序自动确定。
    visible	Boolean	true	节点/边是否可见。
    parent	String	undefined	父节点。
    children	String[]	undefined	子节点/边。
    data	any	undefined	节点/边关联的业务数据。
    """
    def __init__(self,id,markup,attrs,shape,view,zIndex,visible,parent,children,data):
        self._id = id
        self._markup = markup
        self._attrs = attrs
        self._shape = shape
        self._view = view
        self._zIndex = zIndex
        self._visible = visible
        self._parent = parent
        self._children = children
        self._parent = parent
        self._data = data
