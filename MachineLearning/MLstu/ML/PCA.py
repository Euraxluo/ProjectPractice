import numpy as np
class PCA:
    def __init__(self,n_components):#第一个组件
        assert n_components >=1 ,"n_componennts must be valid"
        self.n_components=n_components
        self.components_ = None

    def fit(self,X,eta=0.001,n_iters=1e4):
        assert self.n_components<=X.shape[1],\
            "n_components must not be greater than the feature number of X"

        def first_n_components(n, X, eta=0.001, n_iters=1e4, epsilon=1e-8):
            X_pca = X.copy()  # 以后的计算都是用X_pca
            X_pca = demean(X_pca)
            res = []  # 用来存前n个主成分的列表
            for i in range(n):
                initial_w = np.random.random(X_pca.shape[1])  # 随机初始化一个搜索点
                w = first_component(X_pca, initial_w, eta)
                res.append(w)  # 求出主成分对应的方向
                X_pca = X_pca - X_pca.dot(w).reshape(-1, 1) * w
            return res

        def f(X, w):
            return np.sum((X.dot(w) ** 2)) / len(X)

        def df(X, w):  # △f
            return X.T.dot(X.dot(w)) * 2. / len(X)

        def demean(X):
            return X - np.mean(X, axis=0)

        def diretion(w):
            return w / np.linalg.norm(w)

        def first_component(X, initial_w, eta=0.001, n_iters=1e4, epsilon=1e-8):
            w = diretion(initial_w)
            # 如果我们不做diretion，理所应当需要eta很小，会使搜索次数变多
            cur_iter = 0
            while cur_iter < n_iters:
                gradient = df(X, w)
                last_w = w
                w = w + eta * gradient  # 梯度上升
                w = diretion(w)  # 注意1：应该让w成为一个表方向的单位向量
                if (abs(f(X, w) - f(X, last_w)) < epsilon):
                    break
                cur_iter += 1
            return w

        X_pca = demean(X)
        self.components_ = np.empty(shape=(self.n_components,X.shape[1]))
        for i in range(self.n_components):
            initial_w = np.random.random(X_pca.shape[1])
            w = first_component(X_pca,initial_w,eta,n_iters)
            self.components_[i,:] = w

            X_pca = X_pca - X_pca.dot(w).reshape(-1,1) * w
        return self

    def transform(self,X):#对于用户的数据集，隐射到主成分分量中
        assert X.shape[1] == self.components_.shape[1]

        return X.dot(self.components_.T)

    def inverse_transform(self,X):
        assert X.shape[1] == self.components_.shape[0]

        return  X.dot(self.components_)

    def __repr__(self):
        return "PCA(n_components=%d)" % self.n_components


