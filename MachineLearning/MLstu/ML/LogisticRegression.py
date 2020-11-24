import numpy as np
from .metrics import accuracy_score#分类问题评价函数


class LogisticRegression:
    def __init__(self):
        """初始化LogisticRegression"""
        self.coef_ = None  # 系数
        self.interception_ = None  # 截距
        self._theta = None  # theta

    def _sigmoid(self,t):
        return 1./(1.+np.exp(-t))

    def fit(self, X_train, y_train, eta=0.01, n_iters=1e4):
        """利用X_train,y_train 使用梯度下降 fit LogisticRgression"""
        assert X_train.shape[0] == y_train.shape[0], \
            "the size of X_train must be equal to the size of y_train"

        def J(theta, X_b, y):  # 损失函数
            y_hat = self._sigmoid(X_b.dot(theta))
            try:
                return -np.sum(y*np.log(y_hat) + (1-y)*np.log(1-y_hat)) / len(y)
            except:
                return float('inf')

        def dJ(theta, X_b, y):  # 求导
            return X_b.T.dot(self._sigmoid(X_b.dot(theta)) - y) / len(X_b)


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


    def predict_proba(self, X_predict):###Probability
        """给定一个X_predict,返回表示X_predict的结果概率向量"""
        assert self.interception_ is not None and self.coef_ is not None, \
            "must fit before predict"
        assert X_predict.shape[1] == len(self.coef_), \
            "the feature number of X_predict must be equal to X_train"

        X_b = np.hstack([np.ones((len(X_predict), 1)), X_predict])
        return self._sigmoid(X_b.dot(self._theta))

    def predict(self, X_predict):
        """给定一个X_predict,返回表示X_predict的结果向量"""
        assert self.interception_ is not None and self.coef_ is not None, \
            "must fit before predict"
        assert X_predict.shape[1] == len(self.coef_), \
            "the feature number of X_predict must be equal to X_train"

        proba = self.predict_proba(X_predict)
        return np.array(proba>=0.5 , dtype='int')

    def score(self, X_test, y_test):
        """根据测试数据集 X_test和y_test确定当前模型的准确度"""
        y_predict = self.predict(X_test)
        return accuracy_score(y_test, y_predict)

    def __repr__(self):
        return "LogisticRegression()"