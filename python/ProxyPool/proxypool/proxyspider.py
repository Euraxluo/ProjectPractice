# -*-coding: utf-8-*-
# @Author   : Euraxluo
# @Time     : 19-2-17 下午2:50
# @Software : PyCharm
# 爬取各大代理网站代理资源的爬虫

from .utils import get_page
from pyquery import PyQuery as pq
import re

class CrawlProxyMetaclass(type):#metaclass必须从type派生
    """
        元类，在CrawlProxiesFromWebsite类中加入
        __CrawlFuncList__和__CrawlFuncCount__
        两个参数，分别表示爬虫函数列表，和爬虫函数的数量。
    """
    def __new__(cls, name, bases, attrs): #元类支持对类进行修改,控制类的创建,进而影响类的实例
        count = 0
        attrs['__CrawlFuncList__'] = [] #添加一个列表参数
        for k, v in attrs.items():#attrs是即将创建的class的属性
            if 'crawl_' in k:
                attrs['__CrawlFuncList__'].append(k)
                count += 1
        attrs['__CrawlFuncCount__'] = count #添加一个整型参数

        #attrs['CrawlFuncCount'] = lambda self: return self.count 也可以添加一个名为CrawlFuncCount的函数

        return type.__new__(cls, name, bases, attrs)#动态创建类,并添加额外的属性


class CrawlProxiesFromWebsite(object, metaclass=CrawlProxyMetaclass):

    def proxies_spider(self, crawl_func):
        proxies = [] #创建一个代理列表
        print('Crawl_func:', crawl_func)
        for proxy in eval("self.{}()".format(crawl_func)):#函数执行结束会有一个yield返回,我们对这个yield进行for循环
            print('Getting', proxy, 'from', crawl_func)#打印一下,可以使用日志来做
            proxies.append(proxy)
        return proxies

    ### 这下面的函数都是可以扩展的,但必须用crawl_开头
    #例如:
    # def crawl_ip181(self):
    #     start_url = 'http://www.ip181.com/'#爬取的页面,有页的话,需要format
    #     html = get_page(start_url)#使用我们的公共类进行爬取
    #     ip_adress = re.compile('<tr.*?>\s*<td>(.*?)</td>\s*<td>(.*?)</td>')#构造我们的正则,也可以使用其他解析方式
    #     # \s* 匹配空格，起到换行作用
    #     re_ip_adress = ip_adress.findall(str(html))#查找我们的代理地址,以换行符隔开,也可以使用其他符号
    #     for adress, port in re_ip_adress:#每个函数都要有的,构造我们的代理地址,,返回为yield
    #         result = adress + ':' + port
    #         yield result.replace(' ', '')

    def crawl_kuaidaili(self):
        for page in range(1, 4):
            # 国内高匿代理
            start_url = 'https://www.kuaidaili.com/free/inha/{}/'.format(page)
            html = get_page(start_url)
            ip_adress = re.compile(
                '<td data-title="IP">(.*)</td>\s*<td data-title="PORT">(\w+)</td>'
            )
            re_ip_adress = ip_adress.findall(str(html))
            for adress, port in re_ip_adress:
                result = adress + ':' + port
                yield result.replace(' ', '')

    def crawl_xicidaili(self):
        for page in range(1, 4):
            start_url = 'http://www.xicidaili.com/wt/{}'.format(page)
            html = get_page(start_url)
            ip_adress = re.compile(
                '<td class="country"><img src="http://fs.xicidaili.com/images/flag/cn.png" alt="Cn" /></td>\s*<td>(.*?)</td>\s*<td>(.*?)</td>'
            )
            # \s* 匹配空格，起到换行作用
            re_ip_adress = ip_adress.findall(str(html))
            for adress, port in re_ip_adress:
                result = adress + ':' + port
                yield result.replace(' ', '')

    def crawl_daili66(self, page_count=4):
        start_url = 'http://www.66ip.cn/{}.html'
        urls = [start_url.format(page) for page in range(1, page_count + 1)]
        for url in urls:
            print('Crawling', url)
            html = get_page(url)
            if html:
                doc = pq(html)
                trs = doc('.containerbox table tr:gt(0)').items()
                for tr in trs:
                    ip = tr.find('td:nth-child(1)').text()
                    port = tr.find('td:nth-child(2)').text()
                    yield ':'.join([ip, port])

    def crawl_data5u(self):
        for i in ['gngn', 'gnpt']:
            start_url = 'http://www.data5u.com/free/{}/index.shtml'.format(i)
            html = get_page(start_url)
            ip_adress = re.compile(
                ' <ul class="l2">\s*<span><li>(.*?)</li></span>\s*<span style="width: 100px;"><li class=".*">(.*?)</li></span>'
            )
            # \s * 匹配空格，起到换行作用
            re_ip_adress = ip_adress.findall(str(html))
            for adress, port in re_ip_adress:
                result = adress + ':' + port
                yield result.replace(' ', '')

    def crawl_kxdaili(self):
        for i in range(1, 4):
            start_url = 'http://www.kxdaili.com/ipList/{}.html#ip'.format(i)
            html = get_page(start_url)
            ip_adress = re.compile('<tr.*?>\s*<td>(.*?)</td>\s*<td>(.*?)</td>')
            # \s* 匹配空格，起到换行作用
            re_ip_adress = ip_adress.findall(str(html))
            for adress, port in re_ip_adress:
                result = adress + ':' + port
                yield result.replace(' ', '')

    def crawl_premproxy(self):
        for i in ['China-01', 'China-02', 'China-03', 'China-04', 'Taiwan-01']:
            start_url = 'https://premproxy.com/proxy-by-country/{}.htm'.format(
                i)
            html = get_page(start_url)
            if html:
                ip_adress = re.compile('<td data-label="IP:port ">(.*?)</td>')
                re_ip_adress = ip_adress.findall(str(html))
                for adress_port in re_ip_adress:
                    yield adress_port.replace(' ', '')

    def crawl_xroxy(self):
        for i in ['CN', 'TW']:
            start_url = 'http://www.xroxy.com/proxylist.php?country={}'.format(
                i)
            html = get_page(start_url)
            if html:
                ip_adress1 = re.compile(
                    "title='View this Proxy details'>\s*(.*).*")
                re_ip_adress1 = ip_adress1.findall(str(html))
                ip_adress2 = re.compile(
                    "title='Select proxies with port number .*'>(.*)</a>")
                re_ip_adress2 = ip_adress2.findall(html)
                for adress, port in zip(re_ip_adress1, re_ip_adress2):
                    adress_port = adress + ':' + port
                    yield adress_port.replace(' ', '')
