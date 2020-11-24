# -*-coding: utf-8-*-
# @Author   : Euraxluo
# @Time     : 19-2-16 下午5:28
# @Software : PyCharm

from setuptools import setup, find_packages

setup(
    name = "proxypool",  #包名
    version = "1.0.2",  #版本
    keywords = ("poxypool", "redis"),#关键词列表
    description = "test version proxy pool",  #程序的简单介绍
    long_description = "A proxy pool project modified from Germey/ProxyPool",  #程序的详细介绍
    url = "https://github.com/Euraxluo/ProxyPool", #程序的官网
    download_url = "https://github.com/Euraxluo/ProxyPool.git", #程序的下载地址
    author = "Euraxluo",  #作者
    author_email = "euraxluo@qq.com",  #程序作者的邮箱
  	#maintainer 维护者
	#maintainer_email 维护者的邮箱地址
	packages=[
    	'proxypool'
	],
    py_modules = ['run'],#需要打包的python文件列表
    include_package_data = True,
    platforms = "any",  #程序适用的软件平台列表
    install_requires = [#需要安装的依赖包
        'aiohttp',
        'requests',
        'flask',
        'redis',
        'pyquery',
        'fake-useragent'
    ],
    entry_points = {  #动态发现服务和插件
        'console_scripts': [  #指定命令行工具的名称
            'proxypool_run=run:main'  #工具包名=程序入口
        ]
    },
    license = "apache 2.0",  #程序的授权信息
    zip_safe=False,#安装为文件夹还时打包为egg文件
    classifiers = [#程序所属的分类列表
        'Environment :: Console',
        'Programming Language :: Python :: 3.6',
        'Programming Language :: Python :: Implementation :: CPython'
    ]

)
