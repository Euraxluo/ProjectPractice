# -*-coding: utf-8-*-
# @Author   : Euraxluo
# @Time     : 19-2-15 上午11:07
# @Software : PyCharm
# 代理池的进程调度器
import time
from multiprocessing import Process
import asyncio
import aiohttp
try:
    from aiohttp.errors import ProxyConnectionError,ServerDisconnectedError,ClientResponseError,ClientConnectorError
except:
    from aiohttp import ClientProxyConnectionError as ProxyConnectionError,ServerDisconnectedError,ClientResponseError,ClientConnectorError
from asyncio import TimeoutError
from proxypool.db import RedisClient
from proxypool.error import ResourceDepletionError
from proxypool.proxyspider import CrawlProxiesFromWebsite
from proxypool.conf import *


class ValidityTester(object):
    TEST_API#这个测试的API,时你将要用代理去访问的网点

    def __init__(self):
        self._raw_proxies = None
        self._usable_proxies = []
    def set_raw_proxies(self, proxies):
        self._raw_proxies = proxies
        self._conn = RedisClient()

    async def test_single_proxy(self, proxy):#新的语法async和await,让coroutine的语法更简洁,proxy是单个代理
        """
        text one proxy, if valid, put them to usable_proxies.
        """
        try:
            async with aiohttp.ClientSession() as session:#可以使用aiohttp实现异步检测
                try:
                    if isinstance(proxy, bytes):
                        proxy = proxy.decode('utf-8')#判断类型并转码
                    real_proxy = 'http://' + proxy#构造http协议
                    print('Testing', proxy)
                    async with session.get(TEST_API, proxy=real_proxy, timeout=get_proxy_timeout) as response:#正常的代理验证代码,api,代理,超时时间
                        if response.status == 200:
                            self._conn.put(proxy)#加到队列右侧
                            print('Valid proxy', proxy)
                except (ProxyConnectionError, TimeoutError, ValueError):
                    print('Invalid proxy', proxy)
        except (ServerDisconnectedError, ClientResponseError,ClientConnectorError) as s:
            print(s)
            pass

    def test(self):
        """
        aio test all proxies.
        """
        print('ValidityTester is working')
        try:
            loop = asyncio.get_event_loop() #这里去调用和使用aiohttp的一些方法,使用异步的方法去检测每一个代理
            tasks = [self.test_single_proxy(proxy) for proxy in self._raw_proxies]
            loop.run_until_complete(asyncio.wait(tasks))#构造一个test yield ,然后异步去run
        except ValueError:
            print('Async Error')


class PoolAdder(object):
    """
    add proxy to pool
    """

    def __init__(self, threshold):
        self._threshold = threshold
        self._conn = RedisClient()
        self._tester = ValidityTester()
        self._crawler = CrawlProxiesFromWebsite()#从代理网站抓取的类

    def is_over_threshold(self):
        """
        judge if count is overflow.
        """
        if self._conn.queue_len >= self._threshold:
            return True
        else:
            return False

    def add_to_queue(self):
        print('PoolAdder is working')
        proxy_count = 0
        while not self.is_over_threshold():
            for callback_label in range(self._crawler.__CrawlFuncCount__):#这里对count进行循环
                crawl_func = self._crawler.__CrawlFuncList__[callback_label]#通过下标取函数名
                raw_proxies = self._crawler.proxies_spider(crawl_func)#把函数传给crawl_get_proxies来运行
                # test crawled proxies
                self._tester.set_raw_proxies(raw_proxies)
                self._tester.test()
                proxy_count += len(raw_proxies)
                if self.is_over_threshold():
                    print('proxy enough, waiting to use')
                    break
            if proxy_count == 0:
                raise ResourceDepletionError #如果没有添加上,代理源异常error


class Schedule(object):
    @staticmethod
    def valid_proxy(cycle=VALID_CHECK_CYCLE):
        """
        Get half of proxies which in redis
        """
        conn = RedisClient()#Redis连接对象
        tester = ValidityTester()
        while True:
            print('刷新代理池中...')
            count = int(0.3 * conn.queue_len)#从左侧拿出一半的代理,只剩一个时,看做0个
            if count == 0:#如果队列长度不够了
                print('等待添加代理中...')
                time.sleep(cycle) #设置暂时睡眠,等待添加
                continue
            raw_proxies = conn.get(count)
            tester.set_raw_proxies(raw_proxies)#调用函数添加,raw_proxies设置为类变量
            tester.test()#检测代理是否可用
            time.sleep(cycle)

    @staticmethod
    def check_pool(lower_threshold=POOL_LOWER_THRESHOLD,
                   upper_threshold=POOL_UPPER_THRESHOLD,
                   cycle=POOL_LEN_CHECK_CYCLE):
        """
        If the number of proxies less than lower_threshold, add proxy
        """
        conn = RedisClient()
        adder = PoolAdder(upper_threshold)
        while True:
            if conn.queue_len < lower_threshold:
                adder.add_to_queue()
            time.sleep(cycle)

    def run(self):
        print("调度器运行中...")
        valid_process = Process(target=Schedule.valid_proxy)
        check_process = Process(target=Schedule.check_pool)
        valid_process.start()
        check_process.start()
