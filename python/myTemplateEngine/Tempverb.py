#coding:utf8
import re
import CodeBuilder

class TempvarbSyntaxError(ValueError):
    """让模板语法错误时，抛出异常"""
    pass

class Tempverb(object):
    def __init__(self,text,*contexts):
        """
        :param text: 输入的模板
        :param *contexts: #多个 输入的数据与过滤器函数
        """
        self.all_vars = set()#所有的变量名
        self.loop_vars = set()#属于循环的变量名
        self.context = {}
        for context in contexts:
            self.context.update(context)

        code = CodeBuilder.CodeBuilder()
        code.add_line("def render_function(context,do_dots):")
        code.indent()
        vars_code = code.add_section()#先加个section占位
        code.add_line("result = []")
        code.add_line("append_result = result.append")
        code.add_line("extend_result = result.extend")
        code.add_line("to_str = str")
        buffered = []
        def flush_output():
            """分析模板，分析的结果暂存缓冲区，根据分析的结果向code中添加新代码"""
            if len(buffered) == 1:
                code.add_line("append_result(%s)" % buffered[0])
            elif len(buffered) >1:
                code.add_line("extend_result([%s])" % ",".join(buffered))
            del buffered[:]#请空缓存

        ops_stack = [] #栈，用来保存逻辑
        tokens = re.split(r"(?s)({{.*?}}|{%.*?%}|{#.*?#})", text)
        for token in tokens:
            if token.startswith('{#'):#不操作
                continue
            elif token.startswith('{{'):#将内容转换为python表达式
                expr = self._expr_code(token[2:-2].strip())#得到python表达式
                buffered.append("to_str(%s)"%expr)
            elif token.startswith('{%'):#处理控制结构
                flush_output()
                words = token[2:-2].strip().split()
                if words[0] == 'if':#if 只支持一个表达式如果words大于2就报错
                    if len(words) != 2:
                        self._syntax_error("Don't understand if", token)
                    ops_stack.append('if')#压栈一个if，直到找到一个endif，才出栈
                    code.add_line("if %s:" % self._expr_code(words[1]))#需要转换成为python表达式
                    code.indent()
                elif words[0] == 'for':#for i in
                    if len(words) != 4 or words[2] != 'in':
                        self._syntax_error("Don't understand for", token)
                    ops_stack.append('for')
                    self._variable(words[1],self.loop_vars)#检查变量名是否合法，如果合法则将变量名存入变量集中
                    code.add_line(
                        "for c_%s in %s:"%(
                            words[1],
                            self._expr_code(words[3])
                        )
                    )
                    code.indent()
                elif words[0].startswith('end'):#处理end标签
                    """弹出检查和取消一级缩进"""
                    if len(words) != 1:#只能有一个end*
                        self._syntax_error("Don't understand end",token)
                    end_what = words[0][3:]#获取endif的if
                    if not ops_stack:#如果栈中没有指令
                        self._syntax_error("Too many ends",token)
                    start_what = ops_stack.pop()
                    if start_what != end_what:#判断end*和出栈的指令是否一致
                        self._syntax_error("Mismatched end tag",end_what)
                    code.dedent()
                else:#如果不是以上任何一种，则报错
                    self._syntax_error("Don't understand tag",words[0])
            else:#文本
                if token:
                    buffered.append(repr(token))
        if ops_stack:#解析完所有的模板文件后，需要判断栈是否为空
            self._syntax_error("Unmatched action tag",ops_stack[-1])
        flush_output()#将缓存刷新到结果中
        #all_vars 会包含所有的变量，loop_vars会包含逻辑代码部分的变量，vars_code是之前的占位符
        for var_name in self.all_vars - self.loop_vars:
            vars_code.add_line("c_%s = context[%r]" % (var_name,var_name))
        code.add_line("return ''.join(result)")
        code.dedent()#减小一级缩进
        #运行我们编译好的代码
        self._render_function = code.get_globals()['render_function']

    def _expr_code(self,expr):
        """根据`expr`生成python表达式"""
        if "|" in expr:#先判断有没有管道符
            pips = expr.split("|")
            code = self._expr_code(pips[0])#第一个管道前的数据是数据源
            for func in pips[1:]:
                self._variable(func,self.all_vars)#将过滤器函数更新到all_vars
                code = "c_%s(%s)" % (func,code)#将模板表达式，转换为函数调用的形式
        elif "." in expr:
            dots = expr.split(".")
            code = self._expr_code(dots[0])
            args = ", ".join(repr(d) for d in dots[1:])
            code = "do_dots(%s,%s)" % (code,args)
        else:#如果不含管道和点，直接跟新all_vars，并返回渲染函数内的变量名
            self._variable(expr,self.all_vars)
            code = "c_%s" %expr
        return  code
    def _syntax_error(self,msg,thing):#用于异常报错
        """抛出一个语法错误"""
        raise TempvarbSyntaxError("%s: %r" % (msg,thing))
    def _variable(self,name,vars_set):
        if not re.match(r"[_a-zA-Z][_a-zA-Z0-9]*$",name):
            self._syntax_error("Not a valid name",name)
        vars_set.add(name)

    def render(self,context=None):
        """渲染函数"""
        render_context = dict(self.context)
        if context:
            render_context.update(context)
        return self._render_function(render_context,self._do_dots)

    def _do_dots(self,value,*dots):
        """针对每一次渲染的数据"""
        for dot in dots:
            try:
                value = getattr(value,dot)
            except AttributeError:
                value = value[dot]
            if callable(value):
                value = value()
        return value

