# -*-coding: utf-8-*-
# @Author   : Euraxluo
# @Time     : 19-2-17 下午5:38
# @Software : PyCharm
# 测试文件
import requests
from requests.exceptions import ProxyError,Timeout,ConnectionError,ChunkedEncodingError
import time
from fake_useragent import UserAgent
from proxypool.conf import CHECKKEY,CHECKVALUE
ua = UserAgent()

def get_proxy():
    r = requests.get('http://localhost:5000/proxy/get'+'?'+CHECKKEY+'='+CHECKVALUE)
    proxy = r.text
    return proxy

def get_proxys_count():
    r = requests.get('http://localhost:5000/proxy/count'+'?'+CHECKKEY+'='+CHECKVALUE)
    count = r.text
    return count

def crawl(url, proxy):
    proxies = {'http': proxy}
    r = requests.get(url, proxies=proxies,headers=headers,timeout=8)
    return r

def main():
    count = 0
    while True:
        count = count + 1
        print('开始验证',end=':')
        print(count,end='  ')
        try:
            #请求不同的代理和headers
            global headers,count_proxys
            headers = {'User-Agent': ua.random}
            count_proxys = get_proxys_count()
            proxy = get_proxy()
            print('代理总数： ',count_proxys,'  当前所用的代理：',proxy)
            start_time = time.clock()
            html = crawl('http://www.baidu.com', proxy)
            end_time = time.clock()
            print('代理连接时间： ',(str(end_time-start_time))[:4],' 秒')
            if html.status_code==200:
                print("代理成功")
            else:
                print('代理失败,更换代理')
        except (ChunkedEncodingError,ConnectionError,Timeout,UnboundLocalError,UnicodeError,ProxyError):
            print('代理失败,更换代理')


if __name__ == '__main__':
    main()


