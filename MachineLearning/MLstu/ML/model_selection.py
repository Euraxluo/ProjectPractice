### 封装一个train_test_split
import numpy as np

def train_test_split(X,y,test_ratio = 0.2,seed = None):

    assert X.shape[0] == y.shape[0],"the lines of X be equal to the size of y"
    assert 0.0 <= test_ratio <= 1.0,"test_ratio must legal"

    if seed:
        np.random.seed(seed)
    #Algorithm
    shuffle_indexes = np.random.permutation(len(X))

    test_szie = int(len(X)*test_ratio)
    test_indexes = shuffle_indexes[:test_szie]
    train_indexes = shuffle_indexes[test_szie:]

    X_train = X[train_indexes]
    y_train = y[train_indexes]

    X_test = X[test_indexes]
    y_test = y[test_indexes]

    return X_train,y_train,X_test,y_test

def dJ_debug(theta,X_b,y,epsilon=0.01):
    
    assert X_b.shape[0] == y.shape[0],"the lines of X_b be equal to the size of y"
    assert 0.0 <= epsilon <= 1.0,"epsilon must legal"
    
    res = np.empty(len(theta))
    for i in range(len(theta)):
        theta_plus = theta.copy()
        theta_minus = theta.copy()
        theta_plus[i] +=epsilon
        theta_minus[i] -=epsilon
        res[i] = (J(theta_plus,X_b,y) - J(theta_minus,X_b,y)) / (2*epsilon)
    return res
