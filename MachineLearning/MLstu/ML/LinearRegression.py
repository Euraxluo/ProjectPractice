import numpy as np
from .metrics import r2_score#回归问题评价函数R方

class LinearRegression:
    def __init__(self):
        """初始化LinearRegression"""
        self.coef_ = None #系数
        self.interception_ = None #截距
        self._theta = None #theta

    def fit_normal(self,X_train,y_train):
        """利用X_train,y_train fit 正规方程"""
        assert X_train.shape[0] == y_train.shape[0],\
            "the size of the X_train be equal to the size of y_train"

        X_b = np.hstack([np.ones((len(X_train),1)),X_train])
        self._theta = np.linalg.inv(X_b.T.dot(X_b)).dot(X_b.T).dot(y_train)
        self.interception_ = self._theta[0]
        self.coef_ = self._theta[1:]

        return self
    
    def fit_gd(self,X_train,y_train,eta=0.01,n_iter=1e4):
        """利用X_train,y_train 使用梯度下降 fit """
        assert X_train.shape[0] == y_train.shape[0],\
            "the size of X_train must be equal to the size of y_train"

        def dJ(theta, X_b, y):  # 求导
            #res = np.empty(len(theta))  # 开辟一个空间存MSE(y,hat(y))
            #res[0] = np.sum(X_b.dot(theta) - y)  # 对第0项单独处理
            #for i in range(1, len(theta)):  # 其他的n项
            #    res[i] = (X_b.dot(theta) - y).dot(X_b[:, i])
            #return res * 2 / len(X_b)  # *2/m
            return X_b.T.dot(X_b.dot(theta)-y) * 2./len(X_b)

        def J(theta, X_b, y):  # 损失函数
            try:
                return np.sum((y - X_b.dot(theta)) ** 2) / len(y)
            except:
                return float('inf')

        # 梯度下降算法
        def gradient_descent(X_b, y, initial_theta, eta, n_iters=1e4, epsilon=1e-8):
            theta = initial_theta
            cur_iter = 0

            while cur_iter < n_iters:
                gradient = dJ(theta, X_b, y)
                old_theta = theta
                theta = theta - eta * gradient

                if (abs(J(theta, X_b, y) - J(old_theta, X_b, y)) < epsilon):
                    break
                cur_iter += 1

            return theta

        X_b = np.hstack([np.ones((len(X_train), 1)), X_train])  # 对x加一列,y值为原始的y值
        initial_theta = np.zeros(X_b.shape[1])  # theta初始值为0的向量，列数为特征数+1
        self._theta = gradient_descent(X_b, y_train, initial_theta, eta)

        self.interception_ = self._theta[0]
        self.coef_ = self._theta[1:]
        return self

    def fit_sgd(self,X_train,y_train,n_iters=5,t0=5,t1=50):
        assert X_train.shape[0] == y_train.shape[0],\
            "the size of X_train must be equal to the size of y_train"
        assert n_iters > 1,"fit times must >1"

        def dJ_sgd(theta,X_b_i,y_i):
            return X_b_i * (X_b_i.dot(theta)-y_i) * 2.

        def sgd(X_b,y,initial_theta,n_iters,t0=5,t1=50):
            def learning_rate(t):
                return t0 / (t+t1)
            theta = initial_theta
            m = len(X_b)
            for cur_iter in range(n_iters * m):
                # 超
                # 级
                # 重
                # 要
                # 只用一层循环无法保证，样本都被参照过(应该把样本看5遍)
                # 把样本先乱序排列,可以保证样本都被参照过
                indexes= np.random.permutation(m)
                X_b_new = X_b[indexes]
                y_new = y[indexes]
                for i in range(m):
                    gradient = dJ_sgd(theta, X_b_new[i], y_new[i])#计算梯度
                    theta = theta - learning_rate(cur_iter*m + i) * gradient
                    #learning_rate(cur_iter*m + i) ，learning_rate中应该是，当前遍历的次数
            return theta

        X_b = np.hstack([np.ones((len(X_train),1)),X_train])
        initial_theta = np.random.randn(X_b.shape[1])
        self._theta = sgd(X_b,y_train,initial_theta,n_iters,t0,t1)

        self.interception_ = self._theta[0]
        self.coef_ = self._theta[1:]
        return self



    def predict(self,X_predict):
        """给定一个X_predict,返回表示X_predict的结果向量"""
        assert self.interception_ is not None and self.coef_ is not None,\
            "must fit before predict"
        assert X_predict.shape[1] == len(self.coef_),\
            "the feature number of X_predict must be equal to X_train"

        X_b = np.hstack([np.ones((len(X_predict), 1)), X_predict])
        return X_b.dot(self._theta)

    def score(self,X_test,y_test):
        """根据测试数据集 X_test和y_test确定当前模型的准确度"""
        y_predict = self.predict(X_test)
        return r2_score(y_test,y_predict)


    def __repr__(self):
        return "LinearRegression()"