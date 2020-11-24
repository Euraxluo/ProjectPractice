### 开发笔记

#### 环境安装配置笔记
!(https://euraxluo.github.io/2019-12-20/Hbase基础/]


### merchaants:商家服务相关

需要启动的服务:
    hbase, mysql, kafka, redis,hadoop,zookeeper
需要清空的数据:

    1. hbase 的四张表
        2. mysql 商户数据
        3. /tmp/token/ 下面的优惠券 token 数据
        4. redis 中的数据

1. 创建商户 -- 商户 id 12
    POST: 127.0.0.1:9527/merchants/create
    header: token/passbook-merchants
    {
        "name": "名字",
        "logoUrl": "图片地址",
        "businessLicenseUrl": "营业执照图片地址",
        "phone": "1234567890",
        "address": "商家注册地址"
    }

2. 查看商户信息
    GET: 127.0.0.1:9527/merchants/12
    header: token/passbook-merchants

3. 投放优惠券
    POST: 127.0.0.1:9527/merchants/drop
    header: token/passbook-merchants
    {
        "background": 1,
        "desc": "优惠券",
        "end": "2020-01-30",
        "hasToken": false,
        "id": 12,
        "limit": 1000,
        "start": "2020-01-01",
        "summary": "优惠券简介",
        "title": "优惠券-1"
    }
    {
        "background": 1,
        "desc": "优惠券",
        "end": "2020-01-30",
        "hasToken": true,
        "id": 12,
        "limit": 1000,
        "start": "2020-01-01",
        "summary": "优惠券简介",
        "title": "优惠券-2"
    }

### passbook:消费者相关服务

1. 上传优惠券 token
    GET: 127.0.0.1:9528/upload
    merchantsId - 12
    PassTemplateId: e3ec06eaacb2f1dca901556991df7bb0

2. 创建用户 -- 用户 109452
    POST: 127.0.0.1:9528/passbook/createuser
    {
        "baseInfo": {
            "name": "Euraxluo",
            "age": 10,
            "sex": "m"
        },
        "otherInfo": {
            "phone": "1234567890",
            "address": "北京市海淀区"
        }
    }

3. 库存信息
    GET: 127.0.0.1:9528/passbook/inventoryinfo?userId=109452

4. 获取优惠券 -- 获取的是带有 token 的优惠券
    POST: 127.0.0.1:9528/passbook/gainpasstemplate
    {
        "userId": 109452,
        "passTemplate": {
            "id": 12,
            "title": "优惠券-2",
            "hasToken": true
        }
    }

5. userpassinfo
    GET: 127.0.0.1:9528/passbook/userpassinfo?userId=109452

6. userusedpassinfo
    GET: 127.0.0.1:9528/passbook/userusedpassinfo?userId=109452

7. userusepass
    POST: 127.0.0.1:9528/passbook/userusepass
    {
        "userId": 109452,
        "templateId": "e3ec06eaacb2f1dca901556991df7bb0"
    }

8. 创建评论信息
    POST: 127.0.0.1:9528/passbook/createfeedback
    {
        "userId": 109452,
        "type": "app",
        "templateId": -1,
        "comment": "优惠券详情评论"
    }
    {
        "userId": 109452,
        "type": "pass",
        "templateId": "e3ec06eaacb2f1dca901556991df7bb0",
        "comment": "优惠券详情评论"
    }

9. 查看评论信息
    GET: 127.0.0.1:9528/passbook/getfeedback?userId=109452

