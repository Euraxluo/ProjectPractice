#coding:utf8
import requests
import os
base_url = "http://127.0.0.1:5000/"
api_url = "http://127.0.0.1:5000/todos"
jsonds = {
'todo0' : {'id':'0','title':'标题7777','content':'todo1','done':False},
'todo1' : {'id':'1','title':'标题2','content':'todo2','done':False},
'todo2' : {'id':'2','title':'标题3','content':'todo3','done':False},
'todo3' : {'id':'3','title':'标题4','content':'todo4','done':False},
'todo4' : {'id':'4','title':'标题5','content':'todo5','done':False},
'todo5' : {'id':'5','title':'标题6','content':'todo6','done':False}
}

def printd():
    def decorator(original_func):
        def new_func(*args,**kargs):
            res = original_func(*args, **kargs)
            print(res)
            return res
        return new_func()
    return decorator

# @printd()
# def posttest():#提交todolist的表单
#     for i in range(6):
#         jsond = 'todo'+str(i)
#         r = requests.post(api_url, json=jsonds[jsond])
#     return r.content

# @printd()
# def puttest():
#     print(jsonds['todo1'])
#     return requests.put(api_url+'/0', json=jsonds['todo0']).content

#
# @printd()
# def gettest():#获取所有的todo
#     return requests.get(api_url).content


# @printd()
# def patchtest():
#     return requests.patch(api_url,json=todo).content

# @printd()
# def deltest():
#     return requests.delete(api_url).content

