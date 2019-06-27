# -*- coding:utf-8 -*-

from threading import Thread#线程类
from queue import Queue#队列类

#传一个可迭代对象,把可迭代对象的元素和自身put进队列,如果遇到自身(或者sentinel),就停止
class ThreadedGenerator(object):
    def __init__(self, iterator, sentinel=object(),#以object为默认值,表示传的值必须有效
                 queue_maxsize=0, daemon=False):#遍历器,哨兵,队列,是否以daemon的形式运行

        self._iterator = iterator
        self._sentinel = sentinel
        self._queue = Queue(maxsize=queue_maxsize)#使用队列类初始化,队列默认值
        self._thread = Thread(#使用线程类初始化
            name=repr(iterator),#返回iter的string格式
            target=self._run
        )
        self._thread.daemon = daemon
        self._started = False

    def __repr__(self):#重写repr
        return 'ThreadedGenerator({!r})'.format(self._iterator)

    # 对iter对象进行遍历,如果这个线程不在运行,就跳过,否则放进队列
    def _run(self):
        try:
            for value in self._iterator:
                if not self._started:
                    return
                self._queue.put(value)
        finally:
            self._queue.put(self._sentinel)

    #
    def close(self):
        self._started = False#让启动标志,为false
        try:
            while True:
                self._queue.get(timeout=30)
        except KeyboardInterrupt as e:#捕获KeyboardInterrupt异常,其他通过
            raise e
        except:
            pass

    def __iter__(self): #生成一个可迭代对象,把本类对象作为可迭代对象
        self._started = True#启动标志设为true
        self._thread.start()#启动线程
        for value in iter(self._queue.get, self._sentinel):
            yield value #
        self._thread.join()
        self._started = False

    def __next__(self):
        if not self._started:#如果没有启动
            self._started = True
            self._thread.start()
        value = self._queue.get(timeout=30)
        if value == self._sentinel: #如果遇到哨兵,就停止
            raise StopIteration()
        return value



#  测试
def test():
    def gene():
        i = 0
        while True:
            yield i
            i += 1
    t = gene()
    test2 = ThreadedGenerator(t,sentinel=4)
    for _ in range(10):
        print(next(test2))

    test2.close()


if __name__ == '__main__':
    test()
