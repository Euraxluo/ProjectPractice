# -*-coding: utf-8-*-
# @Author   : Euraxluo
# @Time     : 19-2-17 下午2:58
# @Software : PyCharm
# 配置文件

# Redis数据库的地址和端口
HOST = 'localhost'
PORT = 6379

# 如果Redis有密码，则添加这句密码，否则设置为None或''
PASSWORD = 'euraxluo'

#以下是Flask接口设置的get请求的key和value
CHECKKEY = 'pwd'

CHECKVALUE = 'pwd'

#请求格式为  "url?CHECKKEY=CHECKVALUE"

# 获得代理测试时间界限
get_proxy_timeout = 8

# 代理池数量界限
POOL_LOWER_THRESHOLD = 20
POOL_UPPER_THRESHOLD = 500

# 检查周期
VALID_CHECK_CYCLE = 120
POOL_LEN_CHECK_CYCLE = 20

# 测试API，用百度来测试
TEST_API='http://www.baidu.com'
