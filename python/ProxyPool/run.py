# -*-coding: utf-8-*-
# @Author   : Euraxluo
# @Time     : 19-2-15 上午11:05
# @Software : PyCharm
from proxypool.api import app
from proxypool.schedule import Schedule


def main():
    s = Schedule()#调度器
    s.run()#运行调度器
    app.run()#运行接口

if __name__ == '__main__':
    main()