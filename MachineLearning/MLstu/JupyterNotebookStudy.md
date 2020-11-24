#Jupyter notebooks Study

### 快捷键
常用的快捷键是：
```
Ctrl + Enter: 执行单元格代码
Shift + Enter: 执行单元格代码并且移动到下一个单元格
Alt + Enter: 执行单元格代码，新建并移动到下一个单元格
A: 当前单元格下方创建单元格（命令模式下）
B：当前单元格上方创建单元格 (命令模式下)
当前单元格MarkDown模式和Code模式切换（命令模式下）：m到c为"Y", c到m为"M"
L: 当前单元格内容增加行号（命令模式下）
D+D: 删除单元格
S: 保存
```
### 历史输入和输出变量
与标准 Shell 类似，IPython 中也可以通过 _ 和 _ _ 访问上一次和上上一次的输出。
当你写的单元格多了，肯定会注意到，IPython 中每一次的输入输出都有序号。你可以通过一下方法
```
访问这些输入和输出：
_：访问上一次输出
__：访问上上一次输出
_X：访问历史 X 行输出
_iX：访问历史 X 行输入
其中小写字母 “i”，代表 “in”。
```
### 魔术命令
在 IPython 的会话环境中，所有文件都可以通过 %run 命令来当做脚本执行，并且文件中的变量也会随即导入当前命名空间。
即，对于一个模块文件，你对他使用 %run 命令的效果和 from module import * 相同
这种以 % 开头的命令在 IPython 中被称为魔术命令，用于加强 shell 的功能。
```
常用的魔术命令有： 
%quickref	显示 IPython 快速参考
%magic	显示所有魔术命令的详细文档
%debug	从最新的异常跟踪的底部进入交互式调试器
%pdb	在异常发生后自动进入调试器
%reset	删除 interactive 命名空间中的全部变量
%run script.py	执行 script.py
%prun statement	通过 cProfile 执行对 statement 的逐行性能分析
%time statement	测试 statement 的执行时间
%%time {%% :区域运算}
%timeit statement	多次测试 statement 的执行时间并计算平均值
%who、%who_ls、%whos	显示 interactive 命名空间中定义的变量，信息级别/冗余度可变
%xdel variable	删除 variable，并尝试清除其在 IPython 中的对象上的一切引用
!cmd	在系统 shell 执行 cmd
output=!cmd args	执行cmd 并赋值
%bookmark	使用 IPython 的目录书签系统
%cd direcrory	切换工作目录
%pwd	返回当前工作目录（字符串形式）
%env	返回当前系统变量（以字典形式）
对魔术命令不熟悉的话可以通过 %magic 查看详细文档；对某一个命令不熟悉的话，可以通过 %cmd? 内省机制查看特定文档
```