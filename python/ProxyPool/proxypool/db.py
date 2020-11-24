# -*-coding: utf-8-*-
# @Author   : Euraxluo
# @Time     : 19-2-15 上午11:39
# @Software : PyCharm
# 提供数据库连接和数据存取
import redis
from .error import PoolEmptyError
from .conf import HOST, PORT, PASSWORD,POOL_LOWER_THRESHOLD
from random import randint

class RedisClient(object):
    def __init__(self, host=HOST, port=PORT):
        if PASSWORD:
            self._db = redis.Redis(host=host, port=port, password=PASSWORD)
        else:
            self._db = redis.Redis(host=host, port=port)

    def get(self, count=1):#从左侧拿出count个代理
        """
        get proxies from redis
        """
        proxies = self._db.lrange("proxies", 0, count - 1)#从左侧批量拿出代理
        self._db.ltrim("proxies", count, -1) #只保留[count,-1]这个区间的代理
        return proxies

    def put(self, proxy):#把代理放到队列右侧
        """
        add proxy to right top
        """
        self._db.rpush("proxies", proxy)

    def pop(self):#给api使用，从右边拿出一个代理,并转为utf8编码
        """
        get proxy from right.
        """
        try:
            return self._db.rpop("proxies").decode('utf-8')
        except:
            raise PoolEmptyError#抛出代理池空异常,可以让调度器检测到,进行调度

    def get_buy_index(self):
        """
        get proxy not delete
        """
        random_index = randint(0,self.queue_len)#randint(0,POOL_LOWER_THRESHOLD)
        return self._db.lindex("proxies",index=random_index)



    @property#把这个get方法装饰成一个属性，可以直接用conn.queue_len来调用
    def queue_len(self):#获取队列长度
        """
        get length from queue.
        """
        return self._db.llen("proxies")

    def flush(self):#删除全部代理
        """
        flush db
        """
        self._db.flushall()

if __name__ == '__main__':
    conn = RedisClient()
    print(conn.pop())
