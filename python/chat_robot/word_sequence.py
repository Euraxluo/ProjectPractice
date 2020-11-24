# -*- coding:utf-8 -*-
import numpy as np

# 把句子进行编码化
class WordSequence(object):
    # 字典定义
    PAD_TAG = '<pad>'   #填充标签
    UNK_TAG = '<unk>'   #未知标签
    START_TAG = '<s>'   #开始标记
    END_TAG = '</S>'    #结束标记

    PAD = 0
    UNK = 1
    START = 2
    END = 3

    def __init__(self):
        self.fited = False  #是否训练
        self.dict = {WordSequence.PAD_TAG: WordSequence.PAD, WordSequence.UNK_TAG: WordSequence.UNK,
                     WordSequence.START_TAG: WordSequence.START, WordSequence.END_TAG: WordSequence.END}
    # 编码转换
    def to_index(self, word):   #word 2 index
        assert self.fited, 'WordSequence尚未进行fit操作'
        if word in self.dict:
            return self.dict[word]
        return WordSequence.UNK

    def to_word(self, index):   #index 2 word
        assert self.fited, 'WordSequence尚未进行fit操作'
        for k, v in self.dict.items():
            if v == index:
                return k
        return WordSequence.UNK_TAG

    def size(self):
        assert self.fited, 'WordSequence尚未进行fit操作'
        return len(self.dict) + 1

    def __len__(self):  # 返回字典长度
        return self.size()

    # 训练函数
    def fit(self, sentences, min_count=5, max_count=None, max_features=None):
        assert not self.fited, 'WordSequence只能fit一次'

        count = {}
        for sentence in sentences:
            arr = list(sentence)
            for a in arr:
                if a not in count:
                    count[a] = 0
                count[a] += 1
        if min_count is not None:
            count = {k: v for k, v in count.items() if v >= min_count}
        if max_count is not None:
            count = {k: v for k, v in count.items() if v <= max_count}

        self.dict = {WordSequence.PAD_TAG: WordSequence.PAD, WordSequence.UNK_TAG: WordSequence.UNK,
                     WordSequence.START_TAG: WordSequence.START, WordSequence.END_TAG: WordSequence.END}

        if isinstance(max_features, int):
            count = sorted(list(count.items()), key=lambda x: x[1])#排序,找到最大特征的数目
            if max_features is not None and len(count) > max_features:
                count = count[-int(max_features):]
            for w, _ in count:
                self.dict[w] = len(self.dict)
        else:
            for w in sorted(count.keys()):
                self.dict[w] = len(self.dict)

        self.fited = True

    #把句子编码化
    def transform(self, sentence, max_len=None):
        assert self.fited, 'WordSequence尚未进行fit操作'

        if max_len is not None:
            r = [self.PAD] * max_len
        else:
            r = [self.PAD] * len(sentence)

        for index, a in enumerate(sentence):
            if max_len is not None and index >= len(r):
                break
            r[index] = self.to_index(a)

        return np.array(r)

    # 把句子解码
    def inverse_transform(self, indices, ignore_pad=False, ignore_unk=False, ignore_start=False, ignore_end=False):
        ret = []
        for i in indices:
            word = self.to_word(i)
            if word == WordSequence.PAD_TAG and ignore_pad:
                continue
            if word == WordSequence.UNK_TAG and ignore_unk:
                continue
            if word == WordSequence.START_TAG and ignore_start:
                continue
            if word == WordSequence.END_TAG and ignore_end:
                continue

            ret.append(word)

        return ret


def test():
    ws = WordSequence()
    ws.fit([
        ['你', '好', '啊'],
        ['你', '好', '哦']
    ])

    indice = ws.transform(['我', '们', '好'])
    print(indice)

    back = ws.inverse_transform(indice,ignore_pad=True,ignore_unk=False,ignore_start=True,ignore_end=True)
    print(back)


if __name__ == '__main__':
    test()
