# 函数写法的装饰器
def persists(file_path:str,option:str,step:int=1,jm=False):
    """
    文件持久化装饰器,
    :param file_path:文件存储路径
    :param option:操作 set,getset,get,incr,decr，keys,del
    :param step: 步长
    :param jm:是否加密
    :return: mixed
    """
    def decorator(original_func):
        def new_func(param='',value=''):
            import json
            try:
                cache = json.load(open(file_path, 'r'))
            except (IOError, ValueError):
                cache = {}
            #解析得到key
            import hashlib
            key = str(param)
            if(type(param).__name__ == 'int' and jm==True):
                key = hashlib.new('md5', str(param*65535).encode('utf-8')).hexdigest()
            if(type(param).__name__ == 'str' and jm==True):
                key = hashlib.new('md5', ('cache_'+param).encode('utf-8')).hexdigest()
            """
            根据option选择对应的操作
            """
            if(param =='' and option == 'keys'):
                return cache
            if(option == 'set'): #直接存储
                cache_key = original_func(param,value)    #运行得到新值
                cache[key] = cache_key              #存新值
            if(option == 'getset'):#先获取再存
                if key in cache:
                    cache_key = cache[key]              #得到旧值
                else:
                    cache_key = None
                cache[key] = original_func(param,value)   #存新值
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

            if(option == 'del'):
                if key in cache:
                    cache_key = cache[key]
                    del cache[key]
                else:
                    cache_key = None
            with open(file_path, 'w') as f:
                json.dump(cache, f, indent=4)
            return cache_key
        return new_func
    return decorator

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
