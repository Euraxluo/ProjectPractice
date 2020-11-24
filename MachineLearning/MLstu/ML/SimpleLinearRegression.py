import numpy as np
from .metrics import r2_score
class SimpleLinearRegression1:
    
    def __init__(self):
        """初始化"""
        self.a_ = None
        self.b_ = None
        
    def fit(self,x_train,y_train):
        assert x_train.ndim == 1,"SimpleLinearRegression can only solve single feature training data"
        assert len(x_train) == len(y_train),"the size of x_train must be equal to the size of y_train"
        
        x_mean = np.mean(x_train)
        y_mean = np.mean(y_train)
        
        numerator = 0.0
        denominator = 0.0
        for x_i,y_i in zip(x_train,y_train):
            numerator += (x_i - x_mean)*(y_i - y_mean)
            denominator += (x_i - x_mean)**2
        
        self.a_ = numerator / denominator
        self.b_ = y_mean - self.a_*x_mean
        
        return self
    
    def predict(self,x_predict):
        """给定待预测数据集向量x_perdict，返回结果向量"""
        assert x_predict.ndim ==1,"Simple Linear Regression can only solve single feature training data"
        assert self.a_ is not None and self.b_ is not None,"must fit before predict"
        
        return np.array([self._predict(x) for x in x_predict])#调用单个X预测函数
    
    def _predict(self,x_single):
        """给定单个待预测数据x_single，返回x的预测结果值"""
        return self.a_*x_single + self.b_
    
    def score(self,x_test,y_test):
        y_predict = self.predict(x_test)
        return r2_score(y_test,y_predict)
        
    def __repr__(self):
        return "SimpleLinearRegression1()"
    
    
class SimpleLinearRegression2:
    
    def __init__(self):
        """初始化"""
        self.a_ = None
        self.b_ = None
        
    def fit(self,x_train,y_train):
        assert x_train.ndim == 1,"SimpleLinearRegression can only solve single feature training data"
        assert len(x_train) == len(y_train),"the size of x_train must be equal to the size of y_train"
        
        x_mean = np.mean(x_train)
        y_mean = np.mean(y_train)
        
        numerator = (x_train - x_mean).dot(y_train - y_mean)
        denominator = (x_train - x_mean).dot(x_train - x_mean)
        
        self.a_ = numerator / denominator
        self.b_ = y_mean - self.a_*x_mean
        
        return self
    
    def predict(self,x_predict):
        """给定待预测数据集向量x_perdict，返回结果向量"""
        assert x_predict.ndim ==1,"Simple Linear Regression can only solve single feature training data"
        assert self.a_ is not None and self.b_ is not None,"must fit before predict"
        
        return np.array([self._predict(x) for x in x_predict])#调用单个X预测函数
    
    def _predict(self,x_single):
        """给定单个待预测数据x_single，返回x的预测结果值"""
        return self.a_*x_single + self.b_
    
    
    def score(self,x_test,y_test):
        y_predict = self.predict(x_test)
        return r2_score(y_test,y_predict)
    
    def __repr__(self):
        return "SimpleLinearRegression2()"