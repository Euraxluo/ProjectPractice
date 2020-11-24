import numpy as np
from math import sqrt
from collections import Counter

def KNN_classifier(k,X_train,Y_train,x):
    #assert
    assert 1 <= k <= X_train.shape[0],"k must legal"
    assert X_train.shape[0] == Y_train.shape[0],"the lines of X_train & Y_train must be equal"
    assert X_train.shape[1] == x.shape[0],"the columns of X_train & x must be equal"
    #Algorithm
    distances = [sqrt(np.sum((x_train - x)**2)) for x_train in X_train]
    nearest = np.argsort(distances)

    nearest_label = [Y_train[i] for i in nearest[:k]]
    votes = Counter(nearest_label)

    return votes.most_common(1)[0][0]
