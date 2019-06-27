import numpy as np
from math import sqrt
from collections import Counter
from .metrics import accuracy_score

class KNNclassifier:
    
    def __init__(self, k):#构造函数
        assert k >=1 ,"k must legal"
        
        self.k = k
        self._X_train = None
        self._Y_train = None
    
    def fit(self,X_train,Y_train):#fiting model
        assert X_train.shape[0] == Y_train.shape[0],"the lines of X_train & Y_train must be equal"
        assert self.k <= X_train.shape[0],"the size of X_train must be at least k"
    
        self._X_train = X_train
        self._Y_train = Y_train
        return self
        
    def predict(self,X_predict):
        assert self._X_train is not None and self._Y_train is not None,"must fit before predict"
        assert X_predict.shape[1] == self._X_train.shape[1],"the columns of X_predict & X_train must be equal"
        label_predct = [self.alone_predict(x) for x in X_predict]
        return np.array(label_predct)
    
    def alone_predict(self,x):
        '''x待预测数据，单个分别计算'''
        assert self._X_train.shape[1] == x.shape[0],"the columns of X_train & x must be equal"
        #Algorithm
        distances = [sqrt(np.sum((x_train - x)**2)) for x_train in self._X_train]
        nearest = np.argsort(distances)
        
        nearest_label = [self._Y_train[i] for i in nearest[:self.k]]
        votes = Counter(nearest_label)
        
        return votes.most_common(1)[0][0]

    def score(self,X_test,y_test):
        '''根据预测值和正确的label计算acurracy'''
        label_predict = self.predict(X_test)
        
        return accuracy_score(y_test,label_predict)
    
    def __repr__(self):
        return "KNN(k=%d)" % self.k