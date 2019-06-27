#coding:utf8
class CodeBuilder(object):
    INDENT_STEP = 4 #pep8标准缩进4空格缩进
    def __init__(self,indent=0):
        self.code = []
        self.indent_level = indent

    def add_line(self,line):
        """添加一行代码，自动缩进"""
        self.code.extend([" "*self.indent_level,line,"\n"])

    def indent(self):
        """增加一级缩进"""
        self.indent_level += self.INDENT_STEP

    def dedent(self):
        """减少一级缩进"""
        self.indent_level -= self.INDENT_STEP

    def add_section(self):
        """会在code上追加一个section，并返回"""
        section = CodeBuilder(self.indent_level)
        self.code.append(section)
        return section
    def __str__(self):
        """返回生成的代码字符串，遍历code的内容时如果遇到codeBuilder对象，递归调用其__str__方法"""
        return "".join(str(c) for c in self.code)

    def get_globals(self):
        """运行代码并返回名字空间字典"""
        assert  self.indent_level == 0#检查缩进
        python_source = str(self)#得到生成的代码
        global_namespace = {}#运行代码后得到名字空间
        exec(python_source,global_namespace)
        return global_namespace
