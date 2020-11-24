import argparse
import json
import os
from flask import Flask, g, jsonify, render_template, request, abort
import memory as d

#创建APP
app = Flask(__name__)
app.config.from_object(__name__)


dbfile = 'cache.file'
@d.persists(dbfile,'get')
def get(key):
    return
@d.persists(dbfile,'keys')
def keys():
    return
@d.persists(dbfile,'getset')
def getset(key,value):
    return value
@d.persists(dbfile,'set')
def set(key,value):
    return value
@d.persists(dbfile,'del')
def delk(key):
    return

def flat2(inputlist):
    result = []
    while inputlist:
        head = inputlist.pop(0)
        if isinstance(head, list):
            inputlist = head + inputlist
        else:
            result.append(head)
    return  result

@d.persists(dbfile,'set')
def patch(key,value):
    gets = get(key)
    return flat2([gets,value])



@app.route("/todos",methods=['POST'])
def new_todo():#提交json作todo
    print("post new todo")
    print(request.json)
    return json.dumps(set(request.json['order'],request.json))

@app.route("/todos",methods=['GET'])
def get_todos():#获取所有的todo
    selection = [get(key) for key in  list(keys())]
    return json.dumps(selection)

@app.route("/todos/<string:todo_id>", methods=['DELETE'])
def delete_todo(todo_id):#根据id删除todo
    print("DELETE delete_todo" + todo_id)
    jsonify(delk(todo_id))
    return json.dumps({'code':0});

@app.route("/todos/<string:todo_id>", methods=['GET'])
def get_todo(todo_id):#根据todo_id获取json
    print("GET get_todo" + todo_id)
    todo = get(todo_id)
    return json.dumps(todo)

@app.route("/todos/<string:todo_id>", methods=['PUT'])
def update_todo(todo_id):#根据todo_id,更新内容
    print("PUT update_todo" + todo_id)
    return jsonify(getset(todo_id,request.json))

@app.route("/")
def show_todos():
    return render_template('todo.html')

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Run the Flask todo app')
    parser.add_argument('--setup', dest='run_setup', action='store_true')
    args = parser.parse_args()
    if args.run_setup:
        pass
    else:
        app.run(debug=True)

# print(patch(1,'new'))
# # print(getset(1,'s12131'))
# print(get(1))
# # print(delk(1))