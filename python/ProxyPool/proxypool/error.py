# -*-coding: utf-8-*-
# @Author   : Euraxluo
# @Time     : 19-2-17 下午3:01
# @Software : PyCharm
# 自定义的异常类
class PoolEmptyError(Exception): #代理池空异常

    def __init__(self):
        Exception.__init__(self)

    def __str__(self):
        return repr('异常:代理池为空')

class ResourceDepletionError(Exception):

    def __init__(self):
        Exception.__init__(self)

    def __str__(self):
        return repr('异常:此代理源出错')
