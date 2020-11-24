# 实战过程
学习计划:
1. Linux权限管理imooc/learn/481
2. Linux软件安装管理/447
3. Linux达人养成计划/175
4. Linux服务管理/537
5. iptables防火墙
6. Git/208
7. Tomcat/166
8. Maven/443
9. MySQL/122

## 1. centos 环境配置
## 2. centos JDK安装
java基础/85 /124 /110
## 3. Tomcat 安装
## 4. Maven 安装
1. 用Maven可以方便的创建项目,基于archetype可以创建多种类型的java项目
2. Maven仓库对jar包进行统一管理,避免jar文件的重复拷贝和版本冲突
3. 团队开发,Maven管理项目的RELEASE和SNAPSHOT版本,方便多模块的快速
```maven安装
tar -zxvf apache-maven-x.x.x-bin.tar.gz

//配置环境变量
sudo vim /etc/profile//在最下面加上
export MAVEN_HOME = /developer/apache-maven-x.x.x
export PATH:$PATH:$JAVA_HOME/bin:$MAVEN_HOME/bin
```
常用命令
清除:mvn clean
编译:mvn compile
打包:mvn package
跳过单元测试:mvn clean package -Dmaven.test.skip = true
## 5. vsftpd
1. 简介
免费开源的ftp服务器软件，小巧轻便，支持虚拟用户，支持带宽限制
2. 安装
yum -y install vsftpd
3. 使用`rpm -qa| grep vsftpd`检查是否安装
4. 默认配置文件/etc/vsftpd/vsftpd.conf
5. 创建虚拟用户
>创建用户需要使用的文件夹 mkdir ftpfile
>添加匿名用户:useradd ftpuser -d /ftpfile -s /sbin/nologin
>修改ftpfile权限:chown -R ftpuser.ftpuser /ftpfile
>重设密码:passwd ftpuser

6. 配置
>cd /etc/vsftpd
>sudo vim chroot_list,把刚才新增加的用户添加到此配置中,之后会引用
>cat预览:sudo cat chroot_list ftpuser 
>sudo vim /etc/selinux/config,修改SELINUX = disabled
>如果550则`sudo setsebool -P ftp_home_dir 1 `
>sudo vim /etc/vsftpd/vsftpd.conf
>配置的学习:
>`learning.happymmall.com/env.html`
>`learning.happymmall.com/vsftpdconfig/vsftpd.conf.readme.html`
>`download.happymmall.com/vsftpdconfig.doc`

6. 防火墙配置
>sudo vim /etc/sysconfig/iptables
>-A INPUT -p TCP --dport 61001:62000 -j ACCEPT
>-A OUTPUT -p TCP --dport 61001:62000 -j ACCEPT
>-A INPUT -p TCP --dport 20 -j ACCEPT
>-A OUTPUT -p TCP --dport 20 -j ACCEPT
>-A INPUT -p TCP --dport 21 -j ACCEPT
>-A OUTPUT -p TCP --dport 21 -j ACCEPT
>>sudo service iptables restart 重启防火墙
>>ifconfig  ftp://ip

## 6. Nginx
简介:轻量级Web服务器,反向代理服务器
作用:
1. 可以直接支持Rails和PHP的程序
2. HTTP反向代理服务器
3. 作为负载均衡服务器
4. 作为邮件代理服务器
5. 帮助实现前端动静分离

安装:
1. 依赖安装
>1. yum install gcc
>2. yum install pcre-devel
>3. yum install zlib zlib-devel
>4. yum install openssl openssl-devel #对ssl的支持
>5. 综合命令: yum -y install gcc zlib zlib-devel pcre-devel openssl openssl-devel

2. 下载源码包:`download.happymmal.com/nginx-1.10.2.tar.gz`
3. 解压:`tar -zxvf nginx-1.10.2.tar.gz`
4. 安装: cd 进去`./configure --prefix=/usr/nginx`
5. 创建域名转发配置文件`learning.happymmall.com.conf``img.happymmall.com.conf`
6. 启动:${nginx}/sbin/nginx ${nginx} 代表安装在系统中的路径
7. 重启:${nginx}/sbin/nginx -s reload
8. 访问验证:localhost:80
9. 配置详情:`learning.happymmall.com/nginxconfig`
10. 本地使用需要配置host
>sudo vim /etc/hosts
>添加域名 ip

## 7. 安装其配置mysql

## 表结构设计 以及数据的插入
```sql

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `mmall_cart`
-- ----------------------------
DROP TABLE IF EXISTS `mmall_cart`;
CREATE TABLE `mmall_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `checked` int(11) DEFAULT NULL COMMENT '是否选择,1=已勾选,0=未勾选',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mmall_cart`
-- ----------------------------
BEGIN;
INSERT INTO `mmall_cart` VALUES ('126', '21', '26', '1', '1', '2017-04-13 21:27:06', '2017-04-13 21:27:06');
COMMIT;

-- ----------------------------
--  Table structure for `mmall_category`
-- ----------------------------
DROP TABLE IF EXISTS `mmall_category`;
CREATE TABLE `mmall_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别Id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父类别id当id=0时说明是根节点,一级类别',
  `name` varchar(50) DEFAULT NULL COMMENT '类别名称',
  `status` tinyint(1) DEFAULT '1' COMMENT '类别状态1-正常,2-已废弃',
  `sort_order` int(4) DEFAULT NULL COMMENT '排序编号,同类展示顺序,数值相等则自然排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100032 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mmall_category`
-- ----------------------------
BEGIN;
INSERT INTO `mmall_category` VALUES ('100001', '0', '家用电器', '1', null, '2017-03-25 16:46:00', '2017-03-25 16:46:00'), ('100030', '100005', '进口洋酒', '1', null, '2017-03-25 16:57:05', '2017-03-25 16:57:05');
COMMIT;

-- ----------------------------
--  Table structure for `mmall_order`
-- ----------------------------
DROP TABLE IF EXISTS `mmall_order`;
CREATE TABLE `mmall_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `shipping_id` int(11) DEFAULT NULL,
  `payment` decimal(20,2) DEFAULT NULL COMMENT '实际付款金额,单位是元,保留两位小数',
  `payment_type` int(4) DEFAULT NULL COMMENT '支付类型,1-在线支付',
  `postage` int(10) DEFAULT NULL COMMENT '运费,单位是元',
  `status` int(10) DEFAULT NULL COMMENT '订单状态:0-已取消-10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `send_time` datetime DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mmall_order`
-- ----------------------------
BEGIN;
INSERT INTO `mmall_order` VALUES ('103', '1491753014256', '1', '25', '13998.00', '1', '0', '10', null, null, null, null, '2017-04-09 23:50:14', '2017-04-09 23:50:14'),('117', '1492091141269', '1', '29', '22894.00', '1', '0', '20', '2017-04-13 21:46:06', null, null, null, '2017-04-13 21:45:41', '2017-04-13 21:46:07');
COMMIT;

-- ----------------------------
--  Table structure for `mmall_order_item`
-- ----------------------------
DROP TABLE IF EXISTS `mmall_order_item`;
CREATE TABLE `mmall_order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单子表id',
  `user_id` int(11) DEFAULT NULL,
  `order_no` bigint(20) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `product_image` varchar(500) DEFAULT NULL COMMENT '商品图片地址',
  `current_unit_price` decimal(20,2) DEFAULT NULL COMMENT '生成订单时的商品单价，单位是元,保留两位小数',
  `quantity` int(10) DEFAULT NULL COMMENT '商品数量',
  `total_price` decimal(20,2) DEFAULT NULL COMMENT '商品总价,单位是元,保留两位小数',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_no_index` (`order_no`) USING BTREE,
  KEY `order_no_user_id_index` (`user_id`,`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mmall_order_item`
-- ----------------------------
BEGIN;
INSERT INTO `mmall_order_item` VALUES ('113', '1', '1491753014256', '26', 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '6999.00', '2', '13998.00', '2017-04-09 23:50:14', '2017-04-09 23:50:14'), ('134', '1', '1492091141269', '28', '4+64G送手环/Huawei/华为 nova 手机P9/P10plus青春', '0093f5d3-bdb4-4fb0-bec5-5465dfd26363.jpeg', '1999.00', '2', '3998.00', '2017-04-13 21:45:41', '2017-04-13 21:45:41');
COMMIT;

-- ----------------------------
--  Table structure for `mmall_pay_info`
-- ----------------------------
DROP TABLE IF EXISTS `mmall_pay_info`;
CREATE TABLE `mmall_pay_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `pay_platform` int(10) DEFAULT NULL COMMENT '支付平台:1-支付宝,2-微信',
  `platform_number` varchar(200) DEFAULT NULL COMMENT '支付宝支付流水号',
  `platform_status` varchar(20) DEFAULT NULL COMMENT '支付宝支付状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mmall_pay_info`
-- ----------------------------
BEGIN;
INSERT INTO `mmall_pay_info` VALUES ('53', '1', '1492090946105', '1', '2017041321001004300200116250', 'WAIT_BUYER_PAY', '2017-04-13 21:42:33', '2017-04-13 21:42:33'), ('60', '1', '1492091110004', '1', '2017041321001004300200116396', 'TRADE_SUCCESS', '2017-04-13 21:55:17', '2017-04-13 21:55:17');
COMMIT;

-- ----------------------------
--  Table structure for `mmall_product`
-- ----------------------------
DROP TABLE IF EXISTS `mmall_product`;
CREATE TABLE `mmall_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `category_id` int(11) NOT NULL COMMENT '分类id,对应mmall_category表的主键',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `subtitle` varchar(200) DEFAULT NULL COMMENT '商品副标题',
  `main_image` varchar(500) DEFAULT NULL COMMENT '产品主图,url相对地址',
  `sub_images` text COMMENT '图片地址,json格式,扩展用',
  `detail` text COMMENT '商品详情',
  `price` decimal(20,2) NOT NULL COMMENT '价格,单位-元保留两位小数',
  `stock` int(11) NOT NULL COMMENT '库存数量',
  `status` int(6) DEFAULT '1' COMMENT '商品状态.1-在售 2-下架 3-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mmall_product`
-- ----------------------------
BEGIN;
INSERT INTO `mmall_product` VALUES ('26', '100002', 'Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机', 'iPhone 7，现更以红色呈现。', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg', '241997c4-9e62-4824-b7f0-7425c3c28917.jpeg,b6c56eb0-1748-49a9-98dc-bcc4b9788a54.jpeg,92f17532-1527-4563-aa1d-ed01baa0f7b2.jpeg,3adbe4f7-e374-4533-aa79-cc4a98c529bf.jpeg', '<p><img alt=\"10000.jpg\" src=\"http://img.happymmall.com/00bce8d4-e9af-4c8d-b205-e6c75c7e252b.jpg\" width=\"790\" height=\"553\"><br></p><p><img alt=\"20000.jpg\" src=\"http://img.happymmall.com/4a70b4b4-01ee-46af-9468-31e67d0995b8.jpg\" width=\"790\" height=\"525\"><br></p><p><img alt=\"30000.jpg\" src=\"http://img.happymmall.com/0570e033-12d7-49b2-88f3-7a5d84157223.jpg\" width=\"790\" height=\"365\"><br></p><p><img alt=\"40000.jpg\" src=\"http://img.happymmall.com/50515c02-3255-44b9-a829-9e141a28c08a.jpg\" width=\"790\" height=\"525\"><br></p><p><img alt=\"50000.jpg\" src=\"http://img.happymmall.com/c138fc56-5843-4287-a029-91cf3732d034.jpg\" width=\"790\" height=\"525\"><br></p><p><img alt=\"60000.jpg\" src=\"http://img.happymmall.com/c92d1f8a-9827-453f-9d37-b10a3287e894.jpg\" width=\"790\" height=\"525\"><br></p><p><br></p><p><img alt=\"TB24p51hgFkpuFjSspnXXb4qFXa-1776456424.jpg\" src=\"http://img.happymmall.com/bb1511fc-3483-471f-80e5-c7c81fa5e1dd.jpg\" width=\"790\" height=\"375\"><br></p><p><br></p><p><img alt=\"shouhou.jpg\" src=\"http://img.happymmall.com/698e6fbe-97ea-478b-8170-008ad24030f7.jpg\" width=\"750\" height=\"150\"><br></p><p><img alt=\"999.jpg\" src=\"http://img.happymmall.com/ee276fe6-5d79-45aa-8393-ba1d210f9c89.jpg\" width=\"790\" height=\"351\"><br></p>', '6999.00', '9991', '1', null, '2017-04-13 21:45:41'),('29', '100008', 'Haier/海尔HJ100-1HU1 10公斤滚筒洗衣机全自动带烘干家用大容量 洗烘一体', '门店机型 德邦送货', '173335a4-5dce-4afd-9f18-a10623724c4e.jpeg', '173335a4-5dce-4afd-9f18-a10623724c4e.jpeg,42b1b8bc-27c7-4ee1-80ab-753d216a1d49.jpeg,2f1b3de1-1eb1-4c18-8ca2-518934931bec.jpeg', '<p><img alt=\"1TB2WLZrcIaK.eBjSspjXXXL.XXa_!!2114960396.jpg\" src=\"http://img.happymmall.com/ffcce953-81bd-463c-acd1-d690b263d6df.jpg\" width=\"790\" height=\"920\"><img alt=\"2TB2zhOFbZCO.eBjSZFzXXaRiVXa_!!2114960396.jpg\" src=\"http://img.happymmall.com/58a7bd25-c3e7-4248-9dba-158ef2a90e70.jpg\" width=\"790\" height=\"1052\"><img alt=\"3TB27mCtb7WM.eBjSZFhXXbdWpXa_!!2114960396.jpg\" src=\"http://img.happymmall.com/2edbe9b3-28be-4a8b-a9c3-82e40703f22f.jpg\" width=\"790\" height=\"820\"><br></p>', '4299.00', '9993', '1', '2017-04-13 19:07:47', '2017-04-13 21:45:41');
COMMIT;

-- ----------------------------
--  Table structure for `mmall_shipping`
-- ----------------------------
DROP TABLE IF EXISTS `mmall_shipping`;
CREATE TABLE `mmall_shipping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `receiver_name` varchar(20) DEFAULT NULL COMMENT '收货姓名',
  `receiver_phone` varchar(20) DEFAULT NULL COMMENT '收货固定电话',
  `receiver_mobile` varchar(20) DEFAULT NULL COMMENT '收货移动电话',
  `receiver_province` varchar(20) DEFAULT NULL COMMENT '省份',
  `receiver_city` varchar(20) DEFAULT NULL COMMENT '城市',
  `receiver_district` varchar(20) DEFAULT NULL COMMENT '区/县',
  `receiver_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `receiver_zip` varchar(6) DEFAULT NULL COMMENT '邮编',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mmall_shipping`
-- ----------------------------
BEGIN;
INSERT INTO `mmall_shipping` VALUES ('4', '13', 'geely', '010', '18688888888', '北京', '北京市', '海淀区', '中关村', '100000', '2017-01-22 14:26:25', '2017-01-22 14:26:25'), ('29', '1', '吉利', '13800138000', '13800138000', '北京', '北京', '海淀区', '海淀区中关村', '100000', '2017-04-09 18:33:32', '2017-04-09 18:33:32');
COMMIT;

-- ----------------------------
--  Table structure for `mmall_user`
-- ----------------------------
DROP TABLE IF EXISTS `mmall_user`;
CREATE TABLE `mmall_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码，MD5加密',
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `question` varchar(100) DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) DEFAULT NULL COMMENT '找回密码答案',
  `role` int(4) NOT NULL COMMENT '角色0-管理员,1-普通用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_unique` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mmall_user`
-- ----------------------------
BEGIN;
INSERT INTO `mmall_user` VALUES ('1', 'admin', '427338237BD929443EC5D48E24FD2B1A', 'admin@happymmall.com', '13800138000', '问题', '答案', '1', '2016-11-06 16:56:45', '2017-04-04 19:27:36'), ('21', 'soonerbetter', 'DE6D76FE7C40D5A1A8F04213F2BEFBEE', 'test06@happymmall.com', '13800138000', '105204', '105204', '0', '2017-04-13 21:26:22', '2017-04-13 21:26:22');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
```

## 表结构：
mmall_user:
>唯一索引:username

mmall_cert
>唯一索引:user_id

mmall_order
>唯一索引:order_no

mmall_order_item
>唯一索引:user_id,order_id

mmall_category
mmall_product
mmall_pay_info
mmall_shipping

### 数据流向解析
1. 注册:存储到mmall_user
2. 登陆:读取mmall_user
3. 搜索:分类表:mmall_category(关键字,分类id搜索)和mmall_product(查询结果)
4. 放入购物车:mmall_cart(持久化)
5. 提交订单:mmall_shipping(添加地址)
6. 生成订单:mmall_order_item(一个mmall_order_item对应一个mmall_product)和mmall_order(与mmall_order_item是一对多的关系)
7. 支付订单:接入支付宝,把回调信息存入mmall_pay_info

### 注意点:
1. 不使用外键,便于数据清晰和数据库扩展
2. 不使用sql内置的触发器
3. 对某些字段增加了冗余

### 表的唯一索引
mmall_user的username,不允许用户名重复
mmall_order的order_no,订单号不允许重复
mmall_order_item的order_no以及组合索引:user_id order_no 为了提高检索速度

### 时间戳
所有的表都有`datetime`
create_time:数据创建时间
update_time:数据最近一次更新时间

## 项目初始化
1. 数据库初始化
2. idea
3. 通过maven的archetype创建web项目
4. 初始化项目文件夹结构
5. 发布验证
6. 推送到git仓库
7. maven的pom文件初始化
8. 创建项目包结构
9. 使用Mybatis的支持
>Mybatis-generator 自动化生成数据库交互代码 即dao
>Mybatis-plugin idea插件,实现接口代码和xml的自动错误提示
>Mybatis-pagehelper 分页插件

10. Spring,springMVC配置初始化
>applicationContext.xml配置
>dispatcher-servlet.xml配置

11. Logback初始化
>日志管理logback的初始化及配置
>打开dao层的日志开关

12. ftp服务器配置
13. idea注入和实时编译的配置
>idea使用mybatis及spring scan时,autowired注入时报错处理
>开启Problem窗口

14. Chrome插件
>Restlet client(类似于postman)
>FE助手(格式化json)

## 用户模块开发
### 功能介绍
1. 登录 
2. 用户名验证 
3. 注册 
4. 忘记密码 
5. 提交问题答案  (具有有效期的)
6. 重置密码(忘记密码 登录中的重置密码)
7. 获取用户信息
8. 更新用户信息
9. 退出登录
### 学习目标
1. 横向越权,纵向越权安全漏洞
2. MD5明文加密及增加salt值
3. Guava缓存的使用
4. 高复用服务响应对象的设计思想及抽象分装
5. Session的使用
#### 横向越权,纵向越权安全漏洞
>横向越权:攻击者尝试访问与他拥有相同权限的用户的资源
>纵向越权:低级别攻击者尝试访问高级别用户的资源

### 接口设计
#### 前台用户接口 
##### 1.登录 /user/login.do post(代码需要post方式请求),开放get，方便调试
request

>username,password

response
如果失败status为1,并且把msg返回给前端.
如果成功ststus为0,并且把成功的的信息封装在data中返回,这部分在后端使用泛型

>fail
```
{
    "status": 1,
    "msg": "密码错误"
}
```
>success
```
{
    "status": 0,
    "data": {
        "id": 12,
        "username": "aaa",
        "email": "aaa@163.com",
        "phone": null,
        "role": 0,
        "createTime": 1479048325000,
        "updateTime": 1479048325000
    }
}
```

##### 2.注册 /user/register.do

request
>username,password,email,phone,question,answer

response

>success

```
{
    "status": 0,
    "msg": "校验成功"
}
```

>fail
```
{
    "status": 1,
    "msg": "用户已存在"
}
```

##### 3.检查用户名是否有效 /user/check_valid.do

/check_valid.do?str=admin&type=username就是检查用户名。

request

>str,type
>str可以是用户名也可以是email。对应的type是username和email

response

>success

```
{
    "status": 0,
    "msg": "校验成功"
}
```

>fail

```
{
    "status": 1,
    "msg": "用户已存在"/"email已存在"
}
```

##### 4.获取登录用户信息 /user/get_user_info.do

request

>无参数(从session获取)

response

>success
```
{
    "status": 0,
    "data": {
        "id": 12,
        "username": "aaa",
        "email": "aaa@163.com",
        "phone": null,
        "role": 0,
        "createTime": 1479048325000,
        "updateTime": 1479048325000
    }
}
```

>fail
```
{
    "status": 1,
    "msg": "用户未登录,无法获取当前用户信息"
}
```

##### 5.忘记密码 /user/forget_get_question.do

localhost:8080/user/forget_get_question.do?username=geely

request

>username

response

>success(如果这个username存在)

```
{
    "status": 0,
    "data": "密码提示问题"
}
```

>fail

```
{
    "status": 1,
    "msg": "该用户未设置找回密码问题"
}
```

##### 6.提交问题答案 /user/forget_check_answer.do

localhost:8080/user/forget_check_answer.do?username=aaa&question=aa&answer=sss

request

>username,question,answer

response

正确的返回值里面有一个token，修改密码的时候需要用这个。传递给下一个接口

>success

```
{
    "status": 0,
    "data": "531ef4b4-9663-4e6d-9a20-fb56367446a5"
}
```

>fail
```
{
    "status": 1,
    "msg": "问题答案错误"
}
```
##### 7.忘记密码的重设密码 /user/forget_reset_password.do

localhost:8080/user/forget_reset_password.do?username=aaa&passwordNew=xxx&forgetToken=531ef4b4-9663-4e6d-9a20-fb56367446a5

request

>username,passwordNew,forgetToken

response

>success

```
{
    "status": 0,
    "msg": "修改密码成功"
}
```

>fail
```
{
    "status": 1,
    "msg": "修改密码操作失效"
}
```
或
```
{
    "status": 1,
    "msg": "token已经失效"
}
```

##### 8.登录中状态重置密码 /user/reset_password.do

request

>passwordOld,passwordNew

response

>success

```
{
    "status": 0,
    "msg": "修改密码成功"
}
```

>fail

```
{
    "status": 1,
    "msg": "旧密码输入错误"
}
```

##### 9.登录状态更新个人信息 /user/update_information.do

request

>email,phone,question,answer

response

>success

```
{
    "status": 0,
    "msg": "更新个人信息成功"
}
```

fail

```
{
    "status": 1,
    "msg": "用户未登录"
}
```

##### 10.获取当前登录用户的详细信息，并强制登录 /user/get_information.do

request

>无参数

response

>success

```
{
    "status": 0,
    "data": {
        "id": 1,
        "username": "admin",
        "password": "",
        "email": "admin@163.com",
        "phone": "13800138000",
        "question": "question",
        "answer": "answer",
        "role": 1,
        "createTime": 1478422605000,
        "updateTime": 1491305256000
    }
}
```

>fail
```
{
    "status": 10,
    "msg": "用户未登录,无法获取当前用户信息,status=10,强制登录"
}
```

##### 11.退出登录 /user/logout.do

request

>无

response

>success
```
{
    "status": 0,
    "msg": "退出成功"
}
```

>fail
```
{
    "status": 1,
    "msg": "服务端异常"
}
```

#### 后台用户接口
##### 1.后台管理员登录 /manage/user/login.do

request

>String username,
>String password

response

>success
```
{
    "status": 0,
    "data": {
        "id": 12,
        "username": "aaa",
        "email": "aaa@163.com",
        "phone": null,
        "role": 0,
        "createTime": 1479048325000,
        "updateTime": 1479048325000
    }
}
```
fail
```
{
    "status": 1,
    "msg": "密码错误"
}
```

##### 2.用户列表/manage/user/list.do

request

>pageSize(default=10)
>pageNum(default=1)

response

>success
```
{
    "status": 0,
    "data": {
        "pageNum": 1,
        "pageSize": 3,
        "size": 3,
        "orderBy": null,
        "startRow": 1,
        "endRow": 3,
        "total": 16,
        "pages": 6,
        "list": [
            {
                "id":17,
                "username":"rosen",
                "password":"",
                "email":"rosen1@happymmall.com",
                "phone":"15011111111",
                "question":"啊哈哈",
                "answer":"服不服",
                "role":0,
                "createTime":1489719093000,
                "updateTime":1513682138000
            },
            {
                "id":17,
                "username":"rosen",
                "password":"",
                "email":"rosen1@happymmall.com",
                "phone":"15011111111",
                "question":"啊哈哈",
                "answer":"服不服",
                "role":0,
                "createTime":1489719093000,
                "updateTime":1513682138000
            }
        ],
        "firstPage": 1,
        "prePage": 0,
        "nextPage": 2,
        "lastPage": 6,
        "isFirstPage": true,
        "isLastPage": false,
        "hasPreviousPage": false,
        "hasNextPage": true,
        "navigatePages": 8,
        "navigatepageNums": [
          1,
          2,
          3,
          4,
          5,
          6
        ]
    }
}
```
>fail
```
{
  "status": 10,
  "msg": "用户未登录,请登录"
}
```
或
```
{
  "status": 1,
  "msg": "没有权限"
}
```

### code
#### 1. 部分公共类的实现以及完成用户登录页面
新建一个UserController,加注解`@Controller @RequestMapping("/user/")`
具体code:
```java
package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-7 下午2:55
 */
@Controller//设置为Controller
@RequestMapping("/user/")//设置命名空间
public class UserController {
    @Autowired
    private IUserService iUserService;
    /**
     * @description 用户登录
     * @param [username, password, session]
     * @return java.lang.Object
     * @author Euraxluo
     * @date 18-12-7
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<User> login(String username, String password, HttpSession session){
        //service->mybatis->dao
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }
}
```
在业务逻辑部分,我们的数据流向时Controller->Service->MyBatis->dao
从Service开始,我们新建一个service的接口及其实现类
```java
package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description IUserService的实现分类
 * @Author Euraxluo
 * @Date 18-12-7 下午3:15
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {
    @Autowired//注入Mapper
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //todo 密码登录MD5
        User user = userMapper.selectLogin(username,password);
        if(user == null){//用户名存在,但密码错误,返回的值为空
            return ServerResponse.createByErrorMessage("密码错误");
        }
        //处理返回值密码
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登陆成功",user);
    }
}
```
在service实现类中,我们先完成了登录功能,这部分需要用到status的判断,因此我们又建了几个公共类
ResponseCode:
```java
package com.mmall.common;

/**
 * @ClassName ResponseCode
 * @Description 把status的返回值枚举一下
 * @Author Euraxluo
 * @Date 18-12-7 下午3:37
 */
public enum ResponseCode {
    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

```
ServerResponse:
```java
package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * @ClassName ServerResponse
 * @Description 服务器的返回对象使用泛型
 * @Author Euraxluo
 * @Date 18-12-7 下午3:17
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)//控制空节点(有key无value)被序列化时不被返回
public class ServerResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status){
        this.status = status;
    }
    private ServerResponse(int status,T data){
        this.data = data;
        this.status = status;
    }
    private ServerResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }
    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore//加入这个注解,不会序列化,以下三个get方法会返回
    public boolean isSuccess(){
        return this.status ==ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }
    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }
    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }
    public static <T> ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }
    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }
    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }
    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ServerResponse<T>(errorCode,errorMessage);
    }
}



```
数据流到dao层中,我们看UserMapper:
```java
package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    //传递多个参数,需要使用@Param注解
    User selectLogin(@Param("username") String username,@Param("password") String password);
}
```
在dao中我们通过springmvc注入Mybatis,在对应的UserMapper.xml文件中实现了我们相关的sql语句
```xml
  <resultMap id="BaseResultMap" type="com.mmall.pojo.User" >
    <constructor >
      <idArg column="id" jdbcType="INTEGER" javaType="java.lang.Integer" />
      <arg column="username" jdbcType="VARCHAR" javaType="java.lang.String" />
      <arg column="password" jdbcType="VARCHAR" javaType="java.lang.String" />
      <arg column="email" jdbcType="VARCHAR" javaType="java.lang.String" />
      <arg column="phone" jdbcType="VARCHAR" javaType="java.lang.String" />
      <arg column="question" jdbcType="VARCHAR" javaType="java.lang.String" />
      <arg column="answer" jdbcType="VARCHAR" javaType="java.lang.String" />
      <arg column="role" jdbcType="INTEGER" javaType="java.lang.Integer" />
      <arg column="create_time" jdbcType="TIMESTAMP" javaType="java.util.Date" />
      <arg column="update_time" jdbcType="TIMESTAMP" javaType="java.util.Date" />
    </constructor>
  </resultMap>
  <sql id="Base_Column_List" >
    id, username, password, email, phone, question, answer, role, create_time, update_time
  </sql>
  
  <select id="checkUsername" resultType="int" parameterType="string">
    select count(1) from mmall_user
    where username =#{username}
  </select>

  <select id="selectLogin" resultMap="BaseResultMap" parameterType="map"><!--java.util.Map == map;下面的sql需要什么拿什么-->
    select <include refid="Base_Column_List"/> from mmall_user
    where username = #{username}
    and password = #{password}
  </select>
```
至此用户页面的login功能的数据流完成

#### 2. 登出,注册,校验功能
登出接口:
```java
    /**
     * @description 登出接口
     * @param [session]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 18-12-8
     */
    @RequestMapping(value = "logout.do",method = RequestMethod.GET)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }
```

注册接口:
```java
    /**
     * @description 注册接口
     * @param [user]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 18-12-8
     */
    @RequestMapping(value = "register.do",method = RequestMethod.GET)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<String> register(User user){
        return iUserService.register(user);

    }
```
注册接口的service实现类的方法:
(最开始我们使用的是注释部分的代码,后来为了代码复用,使用了校验部分的代码来重构)
```java
    public ServerResponse<String> register(User user){

        //校验用户名
//        int resultCount = userMapper.checkUsername(user.getUsername());
//        if(resultCount > 0){
//            return ServerResponse.createByErrorMessage("用户名已存在");
//        }

        //为了把代码复用(checkValid会同时在controller中使用,但是其中也可以用来判断),我们把checkValid在这里用来作为校验
        ServerResponse validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }


        //校验邮箱
//        resultCount = userMapper.checkEmail(user.getEmail());
//        if(resultCount > 0){
//            return ServerResponse.createByErrorMessage("email已存在");
//        }

        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        //根据分级来选表
        user.setRole(Const.Role.ROLE_CUSTOMER);//角色设置为普通用户

        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        //把密码插入到数据库
        int resultCount = userMapper.insert(user);
        if(resultCount == 0){
            return ServerResponse.createBySuccessMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }
```
在Service的实现类的注册接口中,使用了相关的MD5加密,我们的MD5加密放在util中,值开放了一个类:
```java
package com.mmall.util;

import org.springframework.util.StringUtils;

import java.security.MessageDigest;

/**
 * @description MD5
 * @param
 * @return
 * @author Euraxluo
 * @date 18-12-8
 */
public class MD5Util {

    //以下两个方法是内部的加密算法
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * @description 返回大写MD5
     * @param [origin, charsetname]
     * @return java.lang.String
     * @author Euraxluo
     * @date 18-12-8
     */
    private static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString.toUpperCase();
    }

    public static String MD5EncodeUtf8(String origin) {
//        origin = origin + PropertiesUtil.getProperty("password.salt", "");
        return MD5Encode(origin, "utf-8");
    }


    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

}

```
同时`checkUsername`和`checkEmail`是我们dao的接口,需要在mapper中写出来
```java
   int checkUsername(String username);

    int checkEmail(String email);
```
UserMapper.xml:
```xml
  <select id="checkUsername" resultType="int" parameterType="string">
    select count(1) from mmall_user
    where username =#{username}
  </select>

  <select id="checkEmail" resultType="int" parameterType="string">
    select count(1) from mmall_user
    where email =#{email}
  </select>

```
并且在录入数据库之前,进行了分组,因此我们在Const中使用了一个内部接口类来实现分组功能
```java
    //通过内部接口类来把常量分组,没有枚举类重,而且里面还是常量
    public interface Role{
        int ROLE_CUSTOMER = 0;//普通用户
        int ROLE_ADMIN = 1;//管理员
    }
```
以及Const.java中的username,email常量
```java

    public static final String EMAIL = "email";

    public static final String USERNAME = "username";

```
最后是我们的校验接口:
```java
    /**
     * @description 校验接口
     * @param [str, type]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 18-12-8
     */
    //为了防止恶意调用接口,需要再加一个校验部分,type用来判断是用户名还是邮箱,对应相应的sql
    @RequestMapping(value = "check_valid.do",method = RequestMethod.GET)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }
```
service实现类:
```java
    public ServerResponse<String> checkValid(String str,String type){
        if(StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("email已存在");
                }
            }
        }else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }
```
我们把这部分的代码复用,重构了register()的校验代码,这个接口使用的是我们原来代码的sql,因此dao层直接调用就好了.

最后的这部分代码就完成了

#### 获取用户的登录信息,以及忘记密码的接口
请求用户信息接口:
其中session获取
```java
    /**
     * @description 请求用户信息
     * @param [session]
     * @return com.mmall.common.ServerResponse<com.mmall.pojo.User>
     * @author Euraxluo
     * @date 18-12-11
     */
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.GET)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取用户当前信息");
    }
```
查询我们的密码问题:
```java
    /**
     * @description 查询并返回我们的问题
     * @param [username]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 18-12-11
     */
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.GET)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }
```
service层的selectQuestion实现类:
```java
    /**
     * @description 使用了查询question的dao接口实现的选择问题实现类,在查询之前需要县校验
     * @param [username]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 18-12-11
     */
    public ServerResponse selectQuestion(String username){
        ServerResponse vaildResponse = this.checkValid(username,Const.USERNAME);
        if(vaildResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if(StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }
```
dao层:
```xml
  <select id="selectQuestionByUsername" resultType="string" parameterType="string">
    select question from mmall_user
    where username = #{username}
  </select>
```
然后得到我们的问题后,进行问题验证:
```java
    /**
     * @description 使用本地缓存检查问题的答案是否正确
     * @param [username, question, answer]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 18-12-11
     */
    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.GET)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }
```
service层验证answer的实现类
```java
    /**
     * @description 检查question的答案
     * @param [username, question, answer]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 18-12-11
     */
    public ServerResponse<String> checkAnswer(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount > 0){
            //问题和问题答案是这个用户的,并且正确.使用util.UUID创建一个token
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey("token_"+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }
```
其中,使用本地缓存来管理token的公共类:
```java
package com.mmall.common;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TokenCache
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-11 下午9:56
 */
public class TokenCache {
    //申明日志
    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    //申明一个内存块作为缓存
    //内存块使用LRU算法进行管理
    //初始化10000,最大值为10000,如果超过10000,就会使用LRU算法,有效期是12个小时
    private static LoadingCache<String,String> loadingCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(1000).expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                //默认的数据加载实现,当调用get取值时,没有找到对应的值,就会调用这个类进行加载
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    //外部调用的方法,可以把token交给Guava来管理
    public static void setKey(String key,String value){
        loadingCache.put(key, value);
    }

    public static String getKey(String key){
        String value = null;
        try {
            if( "null".equals(loadingCache.get(key)) ){
                return null;
            }
            return value;
        } catch (ExecutionException e) {
            logger.error("loadingCache get error",e);
        }
        return null;
    }
}
```
dao层
```java
int checkAnswer(@Param("username")String username,@Param("question") String question,@Param("answer")String answer);
```
mapper-xml:
```xml
  <!--为了验证,我们使用select count 如果答案正确,则有返回值数量>1-->
  <select id="checkAnswer" resultType="int" parameterType="map">
    select count (1) from mmall_user
    where username = #{username}
    and question = #{question}
    and answer = #{answer}
  </select>
```
大概就这么多把,下一个是忘记密码的重置

#### 忘记密码的重置功能
今天把模块写完,测试发现一个bug,我以后不把代码放这里了



## 分类管理模块模块概要与接口设计
功能介绍:
获取节点,增加节点,修改名字,获取分类ID,递归子节点ID
### 节点分析
####1.获取品类子节点(平级)
http://localhost:8080/manage/category/get_category.do http://localhost:8080/manage/category/get_category.do?categoryId=0
http://localhost:8080/manage/category/get_category.do?categoryId=2
/manage/category/get_category.do

request

>categoryId(default=0)

response

>success

```
{
    "status": 0,
    "data": [
        {
            "id": 2,
            "parentId": 1,
            "name": "手机",
            "status": true,
            "sortOrder": 3,
            "createTime": 1479622913000,
            "updateTime": 1479622913000
        },
        {
            "id": 4,
            "parentId": 1,
            "name": "移动座机",
            "status": true,
            "sortOrder": 5,
            "createTime": 1480059936000,
            "updateTime": 1480491941000
        }
    ]
}
```
http://localhost:8080/manage/category/get_category.do?categoryId=19

>fail
```
{
    "status": 10,
    "msg": "用户未登录,请登录"
}
```
或
```
{
    "status": 1,
    "msg": "未找到该品类"
}
```
#### 2.增加节点
/manage/category/add_category.do

request

>parentId(default=0)
>categoryName

response

>success
```
{
    "status": 0,
    "msg": "添加品类成功"
}
```
>fail
```
{
    "status":1,
    "msg": "添加品类失败"
}
```
#### 3.修改品类名字

http://localhost:8080/manage/category/set_category_name.do?categoryId=999&categoryName=%E5%98%BB%E5%98%BB http://localhost:8080/manage/category/set_category_name.do?categoryId=1&categoryName=%E5%98%BB%E5%98%BB
/manage/category/set_category_name.do

request

>categoryId
>categoryName

response

>success
```
{
    "status": 0,
    "msg": "更新品类名字成功"
}
```
>fail
```
{
    "status": 1,
    "msg": "更新品类名字失败"
}
```
#### 4.获取当前分类id及递归子节点categoryId
http://localhost:8080/manage/category/get_deep_category.do?categoryId=100001
/manage/category/get_deep_category.do

request

>categoryId

response

>success
```
{
    "status": 0,
    "data": [
        100009,
        100010,
        100001,
        100006,
        100007,
        100008
    ]
}
```

>fail
```
{
    "status": 1,
    "msg": "无权限"
}
```
### 副文本上传的Util类

```java
package com.mmall.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName FTPUtil
 * @Description TODO
 * @Author Euraxluo
 * @Date 19-1-1 下午9:25
 */
public class FTPUtil {
    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");

    private String ip;
    private int post;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    public FTPUtil(String ip, int post, String user, String pwd) {
        this.ip = ip;
        this.post = post;
        this.user = user;
        this.pwd = pwd;
    }

    /**
     * @description 连接ftp服务器并上传
     * @param [fileList]
     * @return boolean
     * @author Euraxluo
     * @date 19-1-1
     */
    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp,21,ftpUser,ftpPass);
        logger.info("开始连接ftp服务器");
        boolean result = ftpUtil.uploadFile("img",fileList);
        logger.info("成功连接FTP服务器,并且结束上传,上传结果:{}");
        return result;
    }
    /**
     * @description 具体的文件上传
     * @param [remotePath, fileList]
     * @return boolean
     * @author Euraxluo
     * @date 19-1-1
     */
    private boolean uploadFile(String remotePath,List<File> fileList) throws IOException {
        boolean uploaded = true;//上传状态true
        FileInputStream fis = null;
        //连接ftp文件服务器
        if(connectServer(this.ip,this.post,this.user,this.pwd)){
            try {
                ftpClient.changeWorkingDirectory(remotePath);//切换相对路径
                ftpClient.setBufferSize(1024);//缓冲池
                ftpClient.setControlEncoding("UTF-8");//字符集编码
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);//文件类型
                ftpClient.enterLocalPassiveMode();//打开本地被动模式(服务器开启了被动模式的设置)
                for(File fileItem : fileList){
                    fis = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(),fis);//打开文件流,开始上传
                }
            } catch (IOException e) {
                logger.error("上传文件異常",e);
                uploaded = false;//上传失败,改变状态为false
            }finally {
                fis.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }
    /**
     * @description 连接ftp服务器
     * @param [ip, port, user, pwd]
     * @return boolean
     * @author Euraxluo
     * @date 19-1-1
     */
    private boolean connectServer(String ip,int port,String user,String pwd){
        boolean isSuccess = false;//返回状态
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user,pwd);
        } catch (IOException e) {
            logger.error("连接ftp服务器異常",e);
        }
        return isSuccess;
    }
    public static String getFtpIp() {
        return ftpIp;
    }

    public static void setFtpIp(String ftpIp) {
        FTPUtil.ftpIp = ftpIp;
    }

    public static String getFtpUser() {
        return ftpUser;
    }

    public static void setFtpUser(String ftpUser) {
        FTPUtil.ftpUser = ftpUser;
    }

    public static String getFtpPass() {
        return ftpPass;
    }

    public static void setFtpPass(String ftpPass) {
        FTPUtil.ftpPass = ftpPass;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}


```

### 使用Vo完成购物车模块
CartProductVo
```java
public class CartProductVo {//产品和购物车的抽象对象
    //使用Vo来进行逻辑多表连接
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;//购物车中此商品的数量
    private Integer productChecked;//商品是否勾选
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStock;
    private Integer productStatus;
    private BigDecimal productTotalPrice;}
```
CartVo

```java
public class CartVo {
    private List<CartProductVo> cartProductVoList;//购物车有商品的集合
    private BigDecimal cartTotalPrice;//购物车总价
    private Boolean allChecked;//是否已经都勾选
    private String imageHost;
}


```

对Vo对象进行填充


```java
    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");//一定要使用String构造器

        if(CollectionUtils.isNotEmpty(cartList)){//如果查询出来这个人的购物车不是空的,遍历他,填充CartProductVoVo
            for(Cart cartItem : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(cartItem.getUserId());
                cartProductVo.setProductId(cartItem.getProductId());
                //根据productId.查询这个产品,填充到Vo中
                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if(product != null){
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    //判断库存,产品库存不能小于购物车的数量
                    int buyLimitCount = 0;
                    if(product.getStock() >= cartItem.getQuantity()){
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartForQuantity.setId(cartItem.getId());
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算这个商品总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }
                if(cartItem.getChecked() == Const.Cart.CHECKED){
                    //如果勾选,增加到总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        //填充CartVo
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;
    }

```

商业计算中丢失精度

```java
public class BigDecimalUtil {
    private BigDecimalUtil(){
    }
    public static BigDecimal add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }
    public static BigDecimal sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }
    public static BigDecimal mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }
    public static BigDecimal div(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);//四舍五入,保留两位小数
    }
}
```

### 收获地址管理

#### 在插入数据后,需要马上拿到这个生成的id:

```xml
  <insert id="insert" parameterType="com.mmall.pojo.Shipping" useGeneratedKeys="true" keyProperty="id">
    insert into mmall_shipping (id, user_id, receiver_name, 
      receiver_phone, receiver_mobile, receiver_province, 
      receiver_city, receiver_district, receiver_address, 
      receiver_zip, create_time, update_time
      )
    values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, #{receiverName,jdbcType=VARCHAR}, 
      #{receiverPhone,jdbcType=VARCHAR}, #{receiverMobile,jdbcType=VARCHAR}, #{receiverProvince,jdbcType=VARCHAR}, 
      #{receiverCity,jdbcType=VARCHAR}, #{receiverDistrict,jdbcType=VARCHAR}, #{receiverAddress,jdbcType=VARCHAR}, 
      #{receiverZip,jdbcType=VARCHAR}, now(), now()
      )
  </insert>
```

## 项目完结了，不会做了，，，这个就前期需要看看，后面都是业务逻辑，想清楚自己的功能就没问题了，大概就这样，这个项目以后可能会继续更新，但是我现在要去学习ROS了
