#coding:utf8
from . import admin

@admin.route("/")
def index():
    return "<H1>ADMIN</H1>"