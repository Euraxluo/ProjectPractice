# -*-coding: utf-8-*-
# @Author   : Euraxluo
# @Time     : 19-2-17 下午3:00
# @Software : PyCharm
# 把常使用的代码作为公共类
import requests
import asyncio
import aiohttp
from requests.exceptions import ConnectionError
from proxypool.conf import CHECKKEY,CHECKVALUE,POOL_LOWER_THRESHOLD
try:
    from fake_useragent import UserAgent,FakeUserAgentError
except:
    pass



def get_proxy():
    r = requests.get('http://localhost:5000/proxy/get'+'?'+CHECKKEY+'='+CHECKVALUE)
    proxy = r.text
    return proxy

def get_proxys_count():
    r = requests.get('http://localhost:5000/proxy/count'+'?'+CHECKKEY+'='+CHECKVALUE)
    count = r.text
    return int(count)

def check_count():
    proxys_count = get_proxys_count()#获取代理数量
    if(proxys_count > POOL_LOWER_THRESHOLD+1):
        return True
    else:
        return False


def get_page(url, options={}): #页面下载器
    try:
        ua = UserAgent()
    except FakeUserAgentError:
        pass
    base_headers = {
        'User-Agent':  ua.random,
        'Accept-Encoding': 'gzip, deflate, sdch',
        'Accept-Language': 'zh-CN,zh;q=0.8'
    }
    headers = dict(base_headers, **options)
    print('Getting', url)
    try:
        r= requests.get(url, headers=headers)
        print('Getting result', url, r.status_code)
        if r.status_code == 200:
            return r.text
    except ConnectionError:
        print('Crawling Failed', url)
        return None



class Downloader(object):#异步下载器
    """
    一个异步下载器，可以对代理源异步抓取，但是容易被BAN。
    """

    def __init__(self, urls):
        self.urls = urls
        self._htmls = []

    async def download_single_page(self, url):
        async with aiohttp.ClientSession() as session:
            async with session.get(url) as resp:
                self._htmls.append(await resp.text())

    def download(self):
        loop = asyncio.get_event_loop()
        tasks = [self.download_single_page(url) for url in self.urls]
        loop.run_until_complete(asyncio.wait(tasks))

    @property
    def htmls(self):
        self.download()
        return self._htmls

