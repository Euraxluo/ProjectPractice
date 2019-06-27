import numpy as np
b = -30
a = 20
x = np.array([[0,1,1],[0,1,0],[0,0,0],[0,0,1]])#输入变量
d = np.array([1,1,0,1])#期望输出
w = np.array([b,0,0])#权重
i = 0
def sgn(v):
    if v>0:
        return 1
    else:
        return 0

def comy(myw,myx):#将传入的值进行矩阵乘法，然后传递给激活函数
    return sgn(np.dot(myw.T,myx))

def neww(oldw,myd,myx,a):#更新权值
    return oldw+a*(myd-comy(oldw,myx))*myx

print("开始训练,当前权值:",w)
for xn in x:
    w = neww(w,d[i],xn,a)
    i+=1
    print("第", i, "次训练,训练后的权值为:", w)

for xn in x:#训练完毕,开始验证
    print("%d or %d => %d"%(xn[1],xn[2],comy(w,xn)))

