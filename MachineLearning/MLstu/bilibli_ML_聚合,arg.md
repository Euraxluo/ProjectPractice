
## 聚合


```python
import numpy as np
big_array = np.random.rand(1000000)
%timeit sum(big_array)
%timeit np.sum(big_array)#求和
```

    101 ms ± 2.53 ms per loop (mean ± std. dev. of 7 runs, 10 loops each)
    1.41 ms ± 32.3 µs per loop (mean ± std. dev. of 7 runs, 1000 loops each)
    


```python
np.max(big_array)
```




    0.9999995948841754




```python
x = np.arange(16).reshape([4,4])
x
```




    array([[ 0,  1,  2,  3],
           [ 4,  5,  6,  7],
           [ 8,  9, 10, 11],
           [12, 13, 14, 15]])




```python
np.sum(x)#求所有元素的和
```




    120




```python
np.sum(x,axis=0)#axis=0,沿着第一维度，行进行运算{不要第一维度}
```




    array([24, 28, 32, 36])




```python
np.sum(x,axis=1)#压缩列
```




    array([ 6, 22, 38, 54])




```python
np.prod(x)#producte，矩阵的全部元素相乘
```




    0




```python
np.prod(x+1)#全部元素+1，再乘积
```




    2004189184




```python
np.mean(x)#均值,对数字很敏感
```




    7.5




```python
np.median(x)#中位数
```




    7.5




```python
v = np.array([1,1,2,2,10])
np.mean(v)
```




    3.2




```python
np.percentile(big_array,q = 50)#百分位点，小于50%的数(用于绘图)
```




    0.5005241800720106




```python
np.var(big_array)#求方差
```




    0.08334044148374396




```python
np.std(big_array)#标准差
```




    0.28868744600994334




```python
x = np.random.normal(0,1,size = 100000)#均值为0，方差为1，的正态分布随机数
```


```python
np.mean(x)
```




    0.0009150375138361116




```python
np.std(x)
```




    0.9998151035282706



### 索引


```python
np.min(x)#最小值
```




    -5.074717736248483




```python
np.argmin(x)#最小值索引
```




    35954




```python
x[35954]
```




    -5.074717736248483



### 排序和使用索引


```python
x = np.arange(16)
x
```




    array([ 0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15])




```python
np.random.shuffle(x)#乱序
x
```




    array([14,  2,  8, 11,  3,  5,  9,  4,  1, 15,  6, 12, 13,  0, 10,  7])




```python
np.sort(x)#对其引用进行排序
```




    array([ 0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15])




```python
four = x.reshape([4,4])
four
```




    array([[14,  2,  8, 11],
           [ 3,  5,  9,  4],
           [ 1, 15,  6, 12],
           [13,  0, 10,  7]])




```python
np.sort(four,axis=0)#按列排序
```




    array([[ 1,  0,  6,  4],
           [ 3,  2,  8,  7],
           [13,  5,  9, 11],
           [14, 15, 10, 12]])




```python
np.sort(four,axis=1)#按行排序
```




    array([[ 2,  8, 11, 14],
           [ 3,  4,  5,  9],
           [ 1,  6, 12, 15],
           [ 0,  7, 10, 13]])




```python
x.sort()#对x进行排序
x
```




    array([ 0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15])




```python
np.random.shuffle(x)
x
```




    array([15, 14,  7,  1,  6, 13,  5,  2, 10,  4,  9,  3, 11, 12,  8,  0])




```python
np.argsort(x)
```




    array([15,  3,  7, 11,  9,  6,  4,  2, 14, 10,  8, 12, 13,  5,  1,  0],
          dtype=int64)




```python
np.argmax(x)#最大值的索引
```




    0




```python
np.argpartition(x,6)#{返回索引}
```




    array([ 3, 15,  7, 11,  9,  6,  4,  2,  8,  1, 10,  5, 12, 13, 14,  0],
          dtype=int64)



## Fancy Indexing


```python
x = np.arange(9)
x
```




    array([0, 1, 2, 3, 4, 5, 6, 7, 8])




```python
x[3]
```




    3




```python
x[3:8]
```




    array([3, 4, 5, 6, 7])




```python
x[3:8:2]
```




    array([3, 5, 7])




```python
#Fancy index
ind = [3,5,8]
x[ind]
```




    array([3, 5, 8])




```python
x = x.reshape(3,-1)
print(x)
row = np.array([0,1,2])
col = np.array([0,1,2])
x[row,col]#row,与col分别提供横纵坐标
```

    [[0 1 2]
     [3 4 5]
     [6 7 8]]
    




    array([0, 4, 8])




```python
col = [True,False,True]#存在于比较结果
print(x[col])#忽略第二行
```

    [[0 1 2]
     [6 7 8]]
    


```python
x[0:3,col]#忽略第二列
```




    array([[0, 2],
           [3, 5],
           [6, 8]])



### 比较,判断


```python
x>3
```




    array([[False, False, False],
           [False,  True,  True],
           [ True,  True,  True]])




```python
x==3
```




    array([[False, False, False],
           [ True, False, False],
           [False, False, False]])




```python
2*x == 24-2*x
```




    array([[False, False, False],
           [False, False, False],
           [ True, False, False]])




```python
np.sum(x<3)#小于3的个数
```




    3




```python
np.count_nonzero(x<3)#False看作0
```




    3




```python
np.any(x == 0)#有任何元素为True
```




    True




```python
np.all(x >= 0)#全部结果为true，返回true
```




    True




```python
np.sum(x%2 == 0)#查看偶数的个数
```




    5




```python
np.sum(x%2 == 0,axis=0)#沿着行运算{压缩列}
```




    array([2, 1, 2])




```python
np.all(x>0,axis=1)
```




    array([False,  True,  True])




```python
np.sum((x>0) & (x<10))#与运算
```




    8




```python
np.sum(~(x > 0))#非运算
```




    1




```python
x[x<5]#取出<5的部分
```


    ---------------------------------------------------------------------------

    TypeError                                 Traceback (most recent call last)

    <ipython-input-142-32a949a8bfb8> in <module>()
    ----> 1 x(x<5)
    

    TypeError: 'numpy.ndarray' object is not callable



```python
x[x % 2 == 0]##取出偶数的部分
```




    array([0, 2, 4, 6, 8])




```python
x[x[1,0:3]>=4,:]#先取出第二排的元素，再
```




    array([[3, 4, 5],
           [6, 7, 8]])



### Pandas,https://www.cnblogs.com/euraxluo/p/9347512.html
