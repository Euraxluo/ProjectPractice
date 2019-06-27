
## numpy.array基础


```python
import numpy as np
np.__version__
```




    '1.15.1'



### 基本操作


```python
x = np.arange(9).reshape(3,3)
x.ndim#查看你数组维度
```




    2




```python
x.shape#把维度用元组表示
```




    (3, 3)




```python
x.size#元素个数
```




    9



### 数据访问


```python
x
```




    array([[0, 1, 2],
           [3, 4, 5],
           [6, 7, 8]])




```python
x[0,2]
```




    2




```python
x[1][:2]
```




    array([3, 4])




```python
x[0][1:]
```




    array([1, 2])




```python
x[0][::-1]#{::步长为-1}
```




    array([2, 1, 0])



###np.array[2][3]与np.array[2,3]的区别：


```python
x[:2,:2]#前3行前2列
```




    array([[0, 1],
           [3, 4]])




```python
x[:2][:2]
```




    array([[0, 1, 2],
           [3, 4, 5]])




```python
x[0:,::2]#将矩阵按照步长为2取
```




    array([[0, 2],
           [3, 5],
           [6, 8]])




```python
x[0,:]#第一行的全部元素
```




    array([0, 1, 2])




```python
x[:,0]#第一列的全部元素
```




    array([0, 3, 6])




```python
subx = x[:2,:3]#引用。对x或subx改变都会引起对方的改变
subx
```




    array([[0, 1, 2],
           [3, 4, 5]])




```python
subx = x[:2,:3].copy()#将引用copy
subx[0,0] = 100
subx
```




    array([[100,   1,   2],
           [  3,   4,   5]])



### Reshape


```python
x.reshape(1,9)
```




    array([[0, 1, 2, 3, 4, 5, 6, 7, 8]])




```python
x[1:].reshape(1,-1)#将x的后两行变为1维
```




    array([[3, 4, 5, 6, 7, 8]])




```python
x[1:].reshape(6,-1)#将x的后两行变为6维(6行）
```




    array([[3],
           [4],
           [5],
           [6],
           [7],
           [8]])




```python
x[1:].reshape(-1,6)#将x的后两行变为10列，{-1}表示不管行数，auto
```




    array([[3, 4, 5, 6, 7, 8]])




```python
x.reshape(3,-1)#转化为3行
```




    array([[0, 1, 2],
           [3, 4, 5],
           [6, 7, 8]])



### 合并


```python
x = np.array([1,2,3])
y = np.array([4,5,6])
np.concatenate([x,y])#拼接
```




    array([1, 2, 3, 4, 5, 6])




```python
A = np.array([[1,2,3],[4,5,6]])
np.concatenate([A,A],axis=0)
```




    array([[1, 2, 3],
           [4, 5, 6],
           [1, 2, 3],
           [4, 5, 6]])




```python
np.concatenate([A,A],axis=1)
```




    array([[1, 2, 3, 1, 2, 3],
           [4, 5, 6, 4, 5, 6]])




```python
#np.concatenate([A,x])#维度不同，会报错
np.concatenate([A,x.reshape(1,-1)])#将x转化为2维
```




    array([[1, 2, 3],
           [4, 5, 6],
           [1, 2, 3]])




```python
np.vstack([A,x])#在垂直方向自动堆叠
```




    array([[1, 2, 3],
           [4, 5, 6],
           [1, 2, 3]])




```python
np.hstack([A,np.concatenate([A,A],axis=1)])#水平方向堆叠
```




    array([[1, 2, 3, 1, 2, 3, 1, 2, 3],
           [4, 5, 6, 4, 5, 6, 4, 5, 6]])



### 分割


```python
x = np.arange(10)
x
```




    array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9])




```python
x1,x2,x3 = np.split(x,[3,7])
x2
```




    array([3, 4, 5, 6])




```python
x1,x2 = np.split(x,[5])
x2
```




    array([5, 6, 7, 8, 9])




```python
A1,A2 = np.split(A,[1])#在对多维分割，则，元组表示维度
A2
```




    array([[4, 5, 6]])




```python
A1,A2 = np.split(A,[1],axis=1)
A2
```




    array([[2, 3],
           [5, 6]])




```python
A1,A2 = np.vsplit(A,[1])#垂直方向分割
A2
```




    array([[4, 5, 6]])




```python
A1,A2 = np.hsplit(A,[1])#水平方向分割
A2
```




    array([[2, 3],
           [5, 6]])




```python
#例子
data = np.arange(16).reshape((4,4))
data
```




    array([[ 0,  1,  2,  3],
           [ 4,  5,  6,  7],
           [ 8,  9, 10, 11],
           [12, 13, 14, 15]])




```python
#假设前3列为X，最后一列为标记
#每一行是一个样本，要分开X和Y
x,y = np.hsplit(data,[-1])
x
```




    array([[ 0,  1,  2],
           [ 4,  5,  6],
           [ 8,  9, 10],
           [12, 13, 14]])




```python
#对Y处理为矩阵
y[:,0]
```




    array([ 3,  7, 11, 15])



## numpy.array中的运算


```python
L = np.arange(20).reshape((4,5))
L
```




    array([[ 0,  1,  2,  3,  4],
           [ 5,  6,  7,  8,  9],
           [10, 11, 12, 13, 14],
           [15, 16, 17, 18, 19]])



  ### Universal Functions


```python
L + 2
```




    array([[ 2,  3,  4,  5,  6],
           [ 7,  8,  9, 10, 11],
           [12, 13, 14, 15, 16],
           [17, 18, 19, 20, 21]])




```python
L - 2
```




    array([[-2, -1,  0,  1,  2],
           [ 3,  4,  5,  6,  7],
           [ 8,  9, 10, 11, 12],
           [13, 14, 15, 16, 17]])




```python
L  * 2#数乘
```




    array([[ 0,  2,  4,  6,  8],
           [10, 12, 14, 16, 18],
           [20, 22, 24, 26, 28],
           [30, 32, 34, 36, 38]])




```python
L / 2
```




    array([[0. , 0.5, 1. , 1.5, 2. ],
           [2.5, 3. , 3.5, 4. , 4.5],
           [5. , 5.5, 6. , 6.5, 7. ],
           [7.5, 8. , 8.5, 9. , 9.5]])




```python
L//2
```




    array([[0, 0, 1, 1, 2],
           [2, 3, 3, 4, 4],
           [5, 5, 6, 6, 7],
           [7, 8, 8, 9, 9]], dtype=int32)




```python
L%2
```




    array([[0, 1, 0, 1, 0],
           [1, 0, 1, 0, 1],
           [0, 1, 0, 1, 0],
           [1, 0, 1, 0, 1]], dtype=int32)




```python
2/L
```

    c:\ProgramData\Anaconda3\lib\site-packages\ipykernel_launcher.py:1: RuntimeWarning: divide by zero encountered in true_divide
      """Entry point for launching an IPython kernel.
    




    array([[       inf, 2.        , 1.        , 0.66666667, 0.5       ],
           [0.4       , 0.33333333, 0.28571429, 0.25      , 0.22222222],
           [0.2       , 0.18181818, 0.16666667, 0.15384615, 0.14285714],
           [0.13333333, 0.125     , 0.11764706, 0.11111111, 0.10526316]])




```python
L ** 2
```




    array([[  0,   1,   4,   9,  16],
           [ 25,  36,  49,  64,  81],
           [100, 121, 144, 169, 196],
           [225, 256, 289, 324, 361]], dtype=int32)




```python
np.abs(L)#求绝对值
```




    array([[ 0,  1,  2,  3,  4],
           [ 5,  6,  7,  8,  9],
           [10, 11, 12, 13, 14],
           [15, 16, 17, 18, 19]])




```python
np.sin(L)
```




    array([[ 0.        ,  0.84147098,  0.90929743,  0.14112001, -0.7568025 ],
           [-0.95892427, -0.2794155 ,  0.6569866 ,  0.98935825,  0.41211849],
           [-0.54402111, -0.99999021, -0.53657292,  0.42016704,  0.99060736],
           [ 0.65028784, -0.28790332, -0.96139749, -0.75098725,  0.14987721]])




```python
np.cos(L)
```




    array([[ 1.        ,  0.54030231, -0.41614684, -0.9899925 , -0.65364362],
           [ 0.28366219,  0.96017029,  0.75390225, -0.14550003, -0.91113026],
           [-0.83907153,  0.0044257 ,  0.84385396,  0.90744678,  0.13673722],
           [-0.75968791, -0.95765948, -0.27516334,  0.66031671,  0.98870462]])




```python
np.tan(L)
```




    array([[ 0.00000000e+00,  1.55740772e+00, -2.18503986e+00,
            -1.42546543e-01,  1.15782128e+00],
           [-3.38051501e+00, -2.91006191e-01,  8.71447983e-01,
            -6.79971146e+00, -4.52315659e-01],
           [ 6.48360827e-01, -2.25950846e+02, -6.35859929e-01,
             4.63021133e-01,  7.24460662e+00],
           [-8.55993401e-01,  3.00632242e-01,  3.49391565e+00,
            -1.13731371e+00,  1.51589471e-01]])




```python
np.exp(L)#e^l
```




    array([[1.00000000e+00, 2.71828183e+00, 7.38905610e+00, 2.00855369e+01,
            5.45981500e+01],
           [1.48413159e+02, 4.03428793e+02, 1.09663316e+03, 2.98095799e+03,
            8.10308393e+03],
           [2.20264658e+04, 5.98741417e+04, 1.62754791e+05, 4.42413392e+05,
            1.20260428e+06],
           [3.26901737e+06, 8.88611052e+06, 2.41549528e+07, 6.56599691e+07,
            1.78482301e+08]])




```python
np.power(1,L)
```




    array([[1, 1, 1, 1, 1],
           [1, 1, 1, 1, 1],
           [1, 1, 1, 1, 1],
           [1, 1, 1, 1, 1]], dtype=int32)




```python
np.log(L)#logeL
```

    c:\ProgramData\Anaconda3\lib\site-packages\ipykernel_launcher.py:1: RuntimeWarning: divide by zero encountered in log
      """Entry point for launching an IPython kernel.
    




    array([[      -inf, 0.        , 0.69314718, 1.09861229, 1.38629436],
           [1.60943791, 1.79175947, 1.94591015, 2.07944154, 2.19722458],
           [2.30258509, 2.39789527, 2.48490665, 2.56494936, 2.63905733],
           [2.7080502 , 2.77258872, 2.83321334, 2.89037176, 2.94443898]])




```python
np.log10(L)#log10L
```

    c:\ProgramData\Anaconda3\lib\site-packages\ipykernel_launcher.py:1: RuntimeWarning: divide by zero encountered in log10
      """Entry point for launching an IPython kernel.
    




    array([[      -inf, 0.        , 0.30103   , 0.47712125, 0.60205999],
           [0.69897   , 0.77815125, 0.84509804, 0.90308999, 0.95424251],
           [1.        , 1.04139269, 1.07918125, 1.11394335, 1.14612804],
           [1.17609126, 1.20411998, 1.23044892, 1.25527251, 1.2787536 ]])



### 矩阵运算


```python
A = np.arange(1,10).reshape((3,3))
A[2,2]=0
A
```




    array([[1, 2, 3],
           [4, 5, 6],
           [7, 8, 0]])




```python
B = np.array([[1,2,1],[1,1,2],[2,1,1]])
B
```




    array([[1, 2, 1],
           [1, 1, 2],
           [2, 1, 1]])




```python
A+B
```




    array([[2, 4, 4],
           [5, 6, 8],
           [9, 9, 1]])




```python
B-A
```




    array([[ 0,  0, -2],
           [-3, -4, -4],
           [-5, -7,  1]])




```python
A*B
```




    array([[ 1,  4,  3],
           [ 4,  5, 12],
           [14,  8,  0]])




```python
A/B
```




    array([[1. , 1. , 3. ],
           [4. , 5. , 3. ],
           [3.5, 8. , 0. ]])




```python
A.dot(B)#AB
```




    array([[ 9,  7,  8],
           [21, 19, 20],
           [15, 22, 23]])




```python
A.T#A^T
```




    array([[1, 4, 7],
           [2, 5, 8],
           [3, 6, 0]])




```python
E =np.diag([1,1,1])#E
```


```python
np.hstack([B,E])#B|E = B^(-1)
```




    array([[1, 2, 1, 1, 0, 0],
           [1, 1, 2, 0, 1, 0],
           [2, 1, 1, 0, 0, 1]])



#### 逆矩阵和伪逆矩阵
逆矩阵与原矩阵左乘和右乘都为单位矩阵（E）


```python
B_InverseMatrix = np.linalg.inv(B)#B^(-1){B的逆矩阵}
B_InverseMatrix
```




    array([[-0.25, -0.25,  0.75],
           [ 0.75, -0.25, -0.25],
           [-0.25,  0.75, -0.25]])




```python
#用与机器学习中非方阵的求逆
pB,pB1 = np.vsplit(B,[2])#垂直方向分割两排
print(pB)
pinvpB = np.linalg.pinv(pB)#伪逆矩阵
pinvpB
```

    [[1 2 1]
     [1 1 2]]
    




    array([[ 0.09090909,  0.09090909],
           [ 0.63636364, -0.36363636],
           [-0.36363636,  0.63636364]])




```python
#np.linalg{线性代数方法}
np.linalg.det(B)#|B|{B的行列式}
```




    4.0




```python
#B_AdjugateMatrix = B^* = B^(-1)*|B|{B的伴随矩阵}
B_AdjugateMatrix = np.linalg.inv(B) * np.linalg.det(B)
B_AdjugateMatrix
```




    array([[-1., -1.,  3.],
           [ 3., -1., -1.],
           [-1.,  3., -1.]])




```python
A.dot(B_InverseMatrix)#AB^(-1){A/B}
```




    array([[ 0.5 ,  1.5 , -0.5 ],
           [ 1.25,  2.25,  0.25],
           [ 4.25, -3.75,  3.25]])



### 向量和矩阵运算


```python
vector = np.array([1,2,3])
vector
```




    array([1, 2, 3])




```python
A
```




    array([[1, 2, 3],
           [4, 5, 6],
           [7, 8, 0]])




```python
vector + A#向量+矩阵
```




    array([[ 2,  4,  6],
           [ 5,  7,  9],
           [ 8, 10,  3]])




```python
np.vstack([vector]*A.shape[0])#直接vector+A不清楚，先把vector堆叠
```




    array([[1, 2, 3],
           [1, 2, 3],
           [1, 2, 3]])




```python
np.vstack([vector]*A.shape[0])+A#vector堆叠+A = vector+A
```




    array([[ 2,  4,  6],
           [ 5,  7,  9],
           [ 8, 10,  3]])




```python
np.tile(vector,(3,1))#堆叠3行，堆叠1列
```




    array([[1, 2, 3],
           [1, 2, 3],
           [1, 2, 3]])




```python
#vector 的每一项与A的每一行的每一项相乘
vector * A
```




    array([[ 1,  4,  9],
           [ 4, 10, 18],
           [ 7, 16,  0]])




```python
#vector的行与A的列分别相乘相加，最后有vector的行，A的列
vector.dot(A)#把向量堆叠，矩阵乘法
```




    array([30, 36, 15])




```python
#A的每一行与vector看成列向量，并且相乘相加
A.dot(vector)
```




    array([14, 32, 23])



### Python List


```python
L =[ i for i in range(10)]
L
```




    [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]




```python
L[5] = "Machine learning"
L
```




    [0, 1, 2, 3, 4, 'Machine learning', 6, 7, 8, 9]




```python
import array#只能存储一种类型的数组
arr = array.array('i',[i for i in range(10)])
arr
```




    array('i', [0, 1, 2, 3, 4, 5, 6, 7, 8, 9])




```python
#arr[5] = 'machie LEARNING'#只能包含一个类型
```

### numpy.array


```python
nparr = np.array([i for i in range(10)])
nparr
```




    array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9])




```python
nparr.dtype
```




    dtype('int32')




```python
nparr[5] = 45.00
nparr
```




    array([ 0,  1,  2,  3,  4, 45,  6,  7,  8,  9])




```python
nparr2 = np.array([1,2,3.0])
nparr2.dtype
```




    dtype('float64')



## 其他np.array 创建方法


```python
zeros = np.zeros(10,dtype = int)#创建0数组
```


```python
zeros.dtype
```




    dtype('int32')




```python
ones = np.ones(10,dtype = int)#1数组
ones
```




    array([1, 1, 1, 1, 1, 1, 1, 1, 1, 1])




```python
np.full(shape= (3,3),fill_value=0.0,dtype=int)#3阶0矩阵
```




    array([[0, 0, 0],
           [0, 0, 0],
           [0, 0, 0]])



### arange


```python
[i for i in range(0,20,2)]
```




    [0, 2, 4, 6, 8, 10, 12, 14, 16, 18]




```python
np.arange(0,20,2)#起始点，终点，步长
```




    array([ 0,  2,  4,  6,  8, 10, 12, 14, 16, 18])




```python
np.arange(0,10)#起始点，终点，步长为1
```




    array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9])




```python
np.arange(10)#终点，步长为1
```




    array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9])



### linspace


```python
np.linspace(0,20,11)#起始点，终点，取的值的个数，包括起始和终点
```




    array([ 0.,  2.,  4.,  6.,  8., 10., 12., 14., 16., 18., 20.])



### random


```python
np.random.randint(0,10)
```




    2




```python
np.random.randint(0,1,size = (2,5))#区间，size:随机数个数,可以化为矩阵
```




    array([[0, 0, 0, 0, 0],
           [0, 0, 0, 0, 0]])




```python
np.random.seed(666)#指定随机种子，用于test
np.random.randint(4,8,size = (3,3))
```




    array([[4, 6, 5],
           [6, 6, 6],
           [5, 6, 4]])




```python
np.random.normal()#均值为0，方差为1的随机浮点数
```




    -1.1883609275257938




```python
np.random.normal(10,100,size = (3,3))#均值为10，方差为100的随机浮点数,元组
```




    array([[ -66.2461764 ,  -78.45702338,  -87.06415119],
           [-145.87707176,  -11.72599031,   75.51732983],
           [  64.68659307, -152.52001749,  -80.77283341]])




```python
 #查询文档（ipy）
np.random.normal?
```
