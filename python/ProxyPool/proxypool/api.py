# -*-coding: utf-8-*-
# @Author   : Euraxluo
# @Time     : 19-2-15 上午11:07
# @Software : PyCharm
# 用Flask实现外部接口
from flask import Flask, g, request
from .db import RedisClient

from .conf import *

__all__ = ['app']

app = Flask(__name__)

def get_conn():
    """
    Opens a new redis connection if there is none yet for the
    current application context.
    """
    if not hasattr(g, 'redis_client'):#判断g有没有属性redis_client
        g.redis_client = RedisClient()
    return g.redis_client

@app.errorhandler(404)
def not_found_error(error):
	return "404 Not Found", 404

@app.errorhandler(500)
def internal_error(error):
    return "500 ERROR", 500


@app.route('/proxy')
def index():
    return '<h2>Welcome to Proxy Pool System</h2>'

@app.route('/proxy/get')
def get_proxy():
    """
    Get a proxy
    """
    api_checkvalue=request.args.get(CHECKKEY)
    if(api_checkvalue==CHECKVALUE):
        conn = get_conn()
        if(conn.queue_len > POOL_LOWER_THRESHOLD):
            return conn.pop()
        else:
            return conn.get_buy_index()
    else:
        return '<h2>Request ERROR </h2>'

@app.route('/proxy/count')
def get_counts():
    """
    Get the count of proxies
    """
    conn = get_conn()
    return str(conn.queue_len)

if __name__ == '__main__':
    app.run()
