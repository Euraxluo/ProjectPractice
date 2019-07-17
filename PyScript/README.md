# python 学习实验笔记



## 文件持久化装饰器

函数写法
```python
# 函数写法的装饰器
def persist(file_path:str,option:str,step:int=1):
    """
    文件持久化装饰器,
    :param file_path:文件存储路径
    :param option:操作 set,getset,get,incr,decr
    :param step: 步长
    :return: mixed
    """
    def decorator(original_func):
        def new_func(param):
            import json
            try:
                cache = json.load(open(file_path, 'r'))
            except (IOError, ValueError):
                cache = {}
            #解析得到key
            import hashlib
            key = str(param)
            if(type(param).__name__ == 'int'):
                key = hashlib.new('md5', str(param*65535).encode('utf-8')).hexdigest()
            if(type(param).__name__ == 'str'):
                key = hashlib.new('md5', ('cache_'+param).encode('utf-8')).hexdigest()
            """
            根据option选择对应的操作
            """
            cache_key = original_func(param)
            if(option == 'set'): #直接存储
                cache_key = original_func(param)    #运行得到新值
                cache[key] = cache_key              #存新值
            if(option == 'getset'):#先获取再存
                if key in cache:
                    cache_key = cache[key]              #得到旧值
                else:
                    cache_key = None
                cache[key] = original_func(param)   #存新值
            if(option == 'get'):#直接如果有就直接拿，不存
                if key in cache:
                    cache_key = cache[key]
                else:
                    cache_key = None
            if(option == 'incr'):
                if key in cache:
                    cache_key = cache[key]+step
                else:
                    cache_key = step
                cache[key] = cache_key  # 存新值
            if(option == 'decr'):
                if key in cache:
                    cache_key = cache[key]-step
                else:
                    cache_key = -step
                cache[key] = cache_key  # 存新值
            with open(file_path, 'w') as f:
                json.dump(cache, f, indent=4)
            return cache_key
        return new_func
    return decorator
```

类的写法
```python

#类写法的装饰器
import json
import random
from functools import partial
file = "cache.file"
class inner(object):
    def __init__(self, persist, callfunc):
        self.persist = persist
        self.callfunc = callfunc

    def __read(self):
        try:
            return json.load(open(self.persist.file_path, 'r'))
        except (IOError, ValueError):
            return {}

    def __write(self, cache):
        with open(file, "w") as f:
            json.dump(cache, f, indent=4)

    def set(self, cache, params):
        cache_key = self.callfunc(params)  # 运行得到新值
        cache[params] = cache_key
        return cache_key

    def get(self, cache, params):
        return cache.get(params)

    def getset(self, cache, params):
        cache[params] = self.callfunc(params)
        return cache.get(params)

    def incr(self, cache, params):
        cache_key = cache.get(params, 0) + self.persist.step
        cache[params] = cache_key  # 存新值
        return cache_key

    def decr(self, cache, params):
        cache_key = cache.get(params, 0) - self.persist.step
        cache[params] = cache_key  # 存新值
        return cache_key

    def default(self, cache, params):
        return self.callfunc(params)

    def __call__(self, params):
        cache = self.__read()
        if hasattr(self, self.persist.option):
            result = partial(getattr(self, self.persist.option), cache)(params)
        else:
            result = partial(self.default, cache)(params)
        self.__write(cache)
        return result


class persist(object):
    def __init__(self, file_path, option, step=1):
        self.file_path = file_path
        self.option = option
        self.step = step

    def __call__(self, func):
        return inner(self, func)

```
测试用例
```python
@persist(file, 'get')
def test(key):
    return 'get'


@persist(file, 'getset')
def test1(key):
    return 'getset'


@persist(file, 'incr', 5)
def test2(key):
    return 10


@persist(file, 'set')
def test3(key):
    return {'a': 1, 'b': 2}


@persist(file, 'getset')
def test4(key):
    r = [1, 2, 3, 4, 5, 5, key]
    random.shuffle(r)
    return r


@persist(file, 'decr', 11)
def test5(key):
    return -10

```



## python实现策略模式

### namedtuple
```python

# Tuple是元组，不可变对象序列，只能通过下标访问
# 但是namedtuple更强大，可以通过名字进行访问
# 使用方法：
from collections import namedtuple
Animal = namedtuple('Animal','name age type')
cat1 = Animal(name="jerry",age=2,type="cat")#cat1 是一只猫，名字叫做jerry，2岁
print(cat1,cat1.name)
print(cat1[2])#与tuple兼容，可以使用索引取值
```

### 传统的策略模式
```python
from abc import ABC, abstractmethod  # 抽象基类
from collections import namedtuple

Customer = namedtuple('Customer', 'name integral')  # 客户：姓名，积分


#商品类
class productItem:
    """
    购物车中每个商品的数量和单价
    """
    def __init__(self, product, quantity, price):
        self._product = product
        self._quantity = quantity
        self._price = price

    def total(self):
        return self._price * self._quantity

#订单类
class Order:
    """
    订单：用户，购物车，折扣规则
    """
    def __init__(self, customer, cart, promotion=None):
        self._customer = customer
        self._cart = list(cart)  # cart是一个元组，需要做转换为列表
        self._promotion = promotion

    def total(self):
        if not hasattr(self, '_total'):
            self._total = sum(i.total() for i in self._cart)
        return self._total

    def due(self):
        if self._promotion is None:
            discount = 0
        else:
            discount = self._promotion.discount(self)#第二个self应该作为order
        return self.total() - discount

    def __repr__(self):
        fmt = '<订单 总价：{:.2f} 实付：{:.2f}>'
        return fmt.format(self.total(), self.due())

#抽象基类
class Promotion(ABC):
    """
    策略模式，抽象基类
    """
    @abstractmethod
    def discount(order):
        """
        :return:折扣的金额
        """

#第一个策略
class FidelityPromo(Promotion):
    """
    第一个具体策略：为积分1000以上的客户提供5%折扣
    """
    def discount(order):
        return order.total() * 0.05 if order._customer.integral >= 1000 else 0

#第二个策略
class BulkItemPromo(Promotion):
    """
    第二个具体策略：单个商品为20个以上的顾客提供10%折扣
    """
    def discount(order):
        discount = 0
        for i in order._cart:
            if i._quantity >=20:
                discount += i.total() * 0.1
        return discount

#第三个策略
class LargeOrderPromo(Promotion):
    """
    第三个具体策略：不同的商品有10个以上的顾客提供7%折扣
    """
    def discount(order):
        dict_product =  {i._product for i in order._cart}
        if len(dict_product) >= 10:
            return order.total() * 0.07
        return 0

```
### 基于享元的策略模式
```python
#订单类2
class Order2:
    """
    订单：用户，购物车，折扣规则
    """
    def __init__(self, customer, cart, promotion=None):
        self._customer = customer
        self._cart = list(cart)  # cart是一个元组，需要做转换为列表
        self._promotion = promotion

    def total(self):
        if not hasattr(self, '_total'):
            self._total = sum(i.total() for i in self._cart)
        return self._total

    def due(self):
        if self._promotion is None:
            discount = 0
        else:
            discount = self._promotion(self)#第二个self应该作为order
        return self.total() - discount

    def __repr__(self):
        fmt = '<订单 总价：{:.2f} 实付：{:.2f}>'
        return fmt.format(self.total(), self.due())

def FidelityPromoFunc(order):
    return order.total() * 0.05 if order._customer.integral >= 1000 else 0

def BulkItemPromoFunc(order):
    discount = 0
    for i in order._cart:
        if i._quantity >=20:
            discount += i.total() * 0.1
    return discount

def LargeOrderPromoFunc(order):
    dict_product =  {i._product for i in order._cart}
    if len(dict_product) >= 10:
        return order.total() * 0.07
    return 0
```
测试用例
```python
def test():
    joe = Customer("Joe", 50)
    anna = Customer("Anna", 1000)

    cart = [
        productItem('banana', 10, 1),
        productItem('apple', 5, 1),
        productItem('pear', 10, 1)
    ]
    print("策略1：为积分1000以上的客户提供5%折扣")
    print(Order(joe, cart, FidelityPromo))
    print(Order(anna, cart, FidelityPromo))

def test2():
    joe = Customer("Joe", 50)

    cart = [
        productItem('pear', 20, 1)
    ]
    print("策略2：单个商品为20个以上的顾客提供10%折扣")
    print(Order(joe, cart, BulkItemPromo))

def test3():
    joe = Customer("Joe", 50)
    cart = [productItem(str(i),1,1) for i in range(10)]
    print("策略3：不同的商品有10个以上的顾客提供7%折扣")
    print(Order(joe, cart, LargeOrderPromo))
```

## python Socket编程
```python
import socket

HOST, PORT = '', 8888

listen_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
listen_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
listen_socket.bind((HOST, PORT))
listen_socket.listen(1)
print('Serving HTTP on port %s ...' % PORT)
while True:
    client_connection, client_address = listen_socket.accept()
    request = client_connection.recv(1024)
    print(request)

    http_response ="HTTP/1.1 200 ok\r\nconnection: close\r\n\r\n"
client_connection.sendall(http_response)
client_connection.close()
```
## python生成个性二维码
安装步骤：
1. 下载MyQR`sudo pip3 install MyQR`
2. 下载所需资源文件并解压`wget http://labfile.oss.aliyuncs.com/courses/1126/Sources.zip && unzip Sources.zip`
3. 安装FreeImage依赖`wget http://labfile.oss.aliyuncs.com/courses/1126/libfreeimage-3.16.0-linux64.so`
4. 制作个性二维码
```python
from MyQR import myqr
myqr.run(
    words='https://www.shiyanlou.com',
    picture='Sources/shiyanlouLogo.png',
    colorized=True,
    save_name='artistic_Color.png',
)

```
5. myqr.run()的参数

   | 参数|含义	     | 详细     |
   | ---- | ---- | ---- |
   |   words   |  二维码指向链接    |  str，输入链接或者句子作为参数    |
   |   version   |  边长    |int，控制边长，范围是1到40，数字越大边长越大,默认边长是取决于你输入的信息的长度和使用的纠错等级      |
   | level     |纠错等级      |str，控制纠错水平，范围是L、M、Q、H，从左到右依次升高，默认纠错等级为'H'      |
   |picture      |	结合图片      | str，将QR二维码图像与一张同目录下的图片相结合，产生一张黑白图片     |
   | colorized     | 颜色     |bool，使产生的图片由黑白变为彩色的      |
   | contrast     | 对比度     | float，调节图片的对比度，1.0 表示原始图片，更小的值表示更低对比度，更大反之。默认为1.0     |
   | brightness     |亮度      | float，调节图片的亮度，其余用法和取值与 contrast 相同     |
   |save_name      |输出文件名      | str，默认输出文件名是"qrcode.png"     |
   | save_dir| 存储位置 | str，默认存储位置是当前目录|


## python 的压缩函数API
```python
import lz4.frame
import os
import zipfile
import tarfile

class Compress(object):

    @staticmethod
    def lz4(compress:str,data:bytes,mode='wb')->bool:
        """
        使用lz4进行数据压缩
        ;param compress,压缩数据的存储文件路径
        ;param data,压缩数据
        ;param mode，写入模式
        """
        try:
            with lz4.frame.open(compress,mode=mode,content_checksum=False) as fp:
                if type(data).__name__ != 'bytes':
                    data = data.encode('utf-8')
                bytes_written = fp.write(data)
                return bytes_written == len(data)
        except FileNotFoundError as e:
            print(str(e))
            return False

    @staticmethod
    def unlz4(dumpFilePath:str,decompress:str = None)->bool:
        """
        使用lz4压缩算法解压
        ;param dumpFilePath,压缩文件的路径
        ;param dumpFilePath，解压数据的存储地址
        """
        try:
            with lz4.frame.open(dumpFilePath,mode='r',content_checksum=True) as fp:
                output_data = fp.read()
                with open(decompress,'wb') as wf:
                    wf.write(output_data)
                return True
        except RuntimeError as e:
            print('file lz4-decompress faild')
            return False
        except FileNotFoundError as fnfe:
            print(str(fnfe))
            return False

    @staticmethod
    def lz4F(filePath,compressFilePath):
        """
        lz4的压缩文件函数
        ;param filePath
        ;param compressFilePath
        """
        try:
            i=0
            with open(filePath,'rb') as f:
                for line in f:
                    Compress.lz4(compressFilePath,line,'ab')
            return True
        except StopIteration as e:
            print(str(e))

    @staticmethod
    def zip(filepath,zipname,format):
        """
        文件压缩
        ;param filepath,待压缩的路径/文件
        ;param zipname,压缩后的文件名/路径
        ;param format,后缀名
        """
        if  not  os.path.isdir(filepath):
            zipName=zipname+"."+format
            zipName=zipName.decode("utf-8")
            filepath=filepath.decode("utf-8")
            root,file=os.path.split(filepath)
            #os.chdir(rootname)
            f = zipfile.ZipFile(zipName, 'w', zipfile.ZIP_DEFLATED)
            f.write(file)
            f.close()
        else:
            zipName=zipname+"."+format
            if type(zipName).__name__ == 'bytes' or type(filepath).__name__=='bytes':
                zipName=zipName.decode("utf-8")
                filepath=filepath.decode("utf-8")
            f = zipfile.ZipFile(zipName, 'w', zipfile.ZIP_DEFLATED)
            current=os.path.abspath(os.path.join(filepath,".."))
            os.chdir(current)
            filepath=filepath.replace("\\",'/')
            currentRoot=filepath.split("/")[-1]
            print(currentRoot)
            for root, dirs, files in os.walk(currentRoot):
                for file in files:
                    f.write(os.path.join(root,file))
            f.close()
    
    @staticmethod
    def unzip(filepath,savepath=''):
        """
        文件解压缩
        ;param filepath,待解压缩的文件
        ;param zipname,解压缩后的文件名/路径
        """
        if type(filepath).__name__ != 'str' :
            filepath=filepath.decode("utf-8")
        if type(savepath).__name__ != 'str' :
            savepath=savepath.decode("utf-8")
        f = zipfile.ZipFile(filepath,'r')
        for file in f.namelist():
            f.extract(file,savepath)

    @staticmethod
    def zipF(filePath,compressFilePath):
        """
        lz4的压缩文件函数
        :param filePath
        :param compressFilePath
        """
        try:
            i=0
            with open(filePath,'rb') as f:
                for line in f:
                    Compress.zip(compressFilePath,line,'ab')
            return True
        except StopIteration as e:
            print(str(e))

    @staticmethod
    def tar(compress:str,files:str,mod='w')->bool:
        """
        tar打包
        ;param compress,打包后的文件名
        ;param files,待打包的文件
        """
        try:
            _,path=os.path.split(files)
            with tarfile.open(compress,mod) as tp:
                bytes_written = tp.add(files,arcname = path)
                # return bytes_written == len()
        except FileNotFoundError as e:
            print(str(e))
            return False

    @staticmethod
    def untar(compress:str,decompress:str = None)->bool:
        """
        tar解包
        ;param compress,tar压缩文件
        ;param decompress,解压后的文件
        """
        try:
            if not decompress:
                decompress = os.path.split(compress)
            with tarfile.open(compress,'r') as tp:
                tp.extractall(decompress)
        except RuntimeError as e:
            print(str(e))
            return False
if __name__ == "__main__":
    
    #使用lz4将1.md压缩到1_lz.md,然后又解压到1_decom.md
    # Compress().lz4F('testdir/1.md','testdir/1_lz.md')
    # Compress().unlz4('testdir/1_lz.md','testdir/1_decom.md')

    #使用zip来进行压缩和解压缩
    # Compress().zip('testdir','t','zip')
    # Compress().unzip('t.zip','testdirunzip')

    #使用tar来进行文件打包,将1.md，2.md压缩，再解压到testdir/test下
    Compress.tar('testdir/file.tar.gz','testdir/1.md','w')
    Compress.tar('testdir/file.tar.gz','testdir/2.md','a')
    Compress().untar('testdir/file.tar.gz','testdir/test')
```
写的过程的注意点：
1. 文件读取，一般来说使用`with open()`即可，因为with对整个读取过程进行管理，但是当一行的数据非常大时，我们就需要使用open(file,mode,buf),来对缓冲区进行管理，但是比较容易出错（我就出错了。。。）
2. tar函数好像不能直接压缩数据，需要先写入其他文件中
