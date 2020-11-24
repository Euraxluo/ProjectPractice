# 自制模板引擎

1. 模板的处理流程分为解析与渲染阶段
2. 渲染模板需要做以下工作：
    - 管理数据来源（即上下文）
    - 处理逻辑（条件判断，循环）的部分
    - 实现取得成员属性或者键值的功能，实现过滤器调用
3. 重要的关键点：
    - 解析阶段的做法（解释还是编译）
    - 解析阶段最后得到的什么？
    - 渲染阶段渲染的什么
4. 解释型模型
    - 解析阶段最后会生成能够反映模板结构的数据结构。
    - 渲染阶段会遍历整个数据结构并基于预设的指令集生成最后的结果文本。
    - Django使用的模板引擎使用的就是这种方式。

5. 编译模型中
    - 解析阶段最后会生成某种可直接运行的代码。
    - 渲染阶段可直接运行代码得到结果文本。
    - Jinja2与Mako就是使用这种方式的两个典型。

6. 模板到python函数的关键点：
```模板文本
<p>Welcome, {{user_name}}!</p>
<p>Products:</p>
<ul>
{% for product in product_list %}
    <li>{{ product.name }}:
        {{ product.price|format_price }}</li>
{% endfor %}
</ul>
```
模板编译后的python函数
```python
#每一个模板都会被转换为rendor_function函数
#context上下文环境存储数据字典
#do_dots存储用来取得对象属性或者词典键值的函数
def render_function(context, do_dots):
#对输入的字典解包，得到每个变量都用c_作为前缀
    c_user_name = context['user_name']
    c_product_list = context['product_list']
    c_format_price = context['format_price']

#先使用队列存储结果
    result = []
  #append与extend可能会在在代码中多次用到，
  #所以使用append_result与extend_result来引用它们
    append_result = result.append
    extend_result = result.extend
    #Python检索局部空间比检索内置空间早，
    # 所以把str存储在局部变量中也是一种优化。
    to_str = str
#使用append_result与extend_result把结果串起来，
# 其中需要替换的部分就用输入数据替换，
# 最后把队列合成一个字符串作为结果文本返回。
    extend_result([
        '<p>Welcome, ',
        to_str(c_user_name),
        '!</p>\n<p>Products:</p>\n<ul>\n'
    ])
    for c_product in c_product_list:
        extend_result([
            '\n    <li>',
            to_str(do_dots(c_product, 'name')),
            ':\n        ',
            to_str(c_format_price(do_dots(c_product, 'price'))),
            '</li>\n'
        ])
    append_result('\n</ul>\n')
    return ''.join(result)
```


编程：
Tempverb.py和CodeBuilder.py是核心代码，test_templite.py是覆盖性测试代码


开源协议：
1. 申明：
    - 本项目学习自**500 lines or less**项目
    - 作者博客：http://nedbatchelder.com 。
    - 项目代码使用 **MIT** 协议，
    - 项目文档使用 `http://creativecommons.org/licenses/by/3.0/legalcode` 协议。
2. lisence
    **MIT**

