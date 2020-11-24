import numpy as np
class StandardScaler:

    def __init__(self):
        self.mean_ = None
        self.scale_ = None

    def fit(self,X):
        """ 根据训练数据集获得数据的均值和方差"""
        assert X.ndim == 2,"The dimension of X must be 2"

        self.mean_ = np.array([np.mean(X[:,i]) for i in range(X.shape[1])])
        self.scale_ = np.array([np.std(X[:,i]) for i in range(X.shape[1])])

        return self

    def transform(self,X):
        """将X根据这个StandardScaler进行均值方差归一化处理"""
        assert X.ndim == 2,"The dimension of X must be 2"
        assert self.mean_ is not None and self.scale_ is not None,"must fit before transform"
        assert X.shape[1] == len(self.mean_),"the feature number of X must be equal to mean_ and std_"

        returnX = np.empty(shape=X.shape,dtype=float)#空矩阵存储返回的归一化数据
        for column in range(X.shape[1]):
            #分列计算，每一列分别取对应列的均值方差
            returnX[:,column] = (X[:,column] - self.mean_[column]) / self.scale_[column]
        return returnX
