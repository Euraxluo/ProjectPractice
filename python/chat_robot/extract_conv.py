# -*- coding:utf-8 -*-

import re
import pickle
import sys
from tqdm import tqdm
from word_sequence import WordSequence

# 语料清洗
##合并和切分,去掉不常用的符号
def make_split(line):
    if re.match(r'.*([，···?!\.,!？])$', ''.join(line)):
        return []
    return [', ']

##筛选出好句子和坏句子,判断英文和数字是不是太多了
def good_line(line):
    if len(re.findall(r'[a-zA-Z0-9]', ''.join(line))) > 2:
        return False
    return True

##替换掉多余的字符
def regular(sen):
    sen = re.sub(r'\.{3,100}', '···', sen)
    sen = re.sub(r'···{2,100}', '···', sen)
    sen = re.sub(r'[,]{1,100}', '，', sen)
    sen = re.sub(r'[\.]{1,100}', '。', sen)
    sen = re.sub(r'[\?]{1,100}', '？', sen)
    sen = re.sub(r'[!]{1,100}', '！', sen)
    return sen


def main(limit=20, x_limit=3, y_limit=6):#一个句子最多只能有20个字符
    #解压文件
    print('extract lines')#
    fp = open('dgk_shooter_min_test.conv', 'r', errors='ignore', encoding='utf-8')

    #去掉M空格,和斜杠,丢弃,无效的句子
    groups = []
    group = []
    for line in tqdm(fp):
        if line.startswith('M '): #表示这一行是句子,需要处理
            line = line.replace('\n', '')
            if '/' in line:#如果这一行有'/',我们就从第二个字符开始以'/'切分
                line = line[2:].split('/')
            else:
                line = list(line[2:])
            line = line[:-1]

            group.append(list(regular(''.join(line))))
        else:
            if group:
                groups.append(group)
                group = []
    if group:
        groups.append(group)
        group = []
    print('extract group')


    # 定义问答对
    x_data = []
    y_data = []
    for group in tqdm(groups):#tqdm可以显示进度
        for i, line in enumerate(group): #把他化为枚举类
            last_line = None
            if i > 0:#表示最少有两行
                last_line = group[i-1]
                if not good_line(last_line):
                    last_line = None
            next_line = None
            if i < len(group) - 1:
                next_line = group[i+1]
                if not good_line(next_line):
                    next_line = None

            next_next_line = None
            if i < len(group) - 2:
                next_next_line = group[i + 2]
                if not good_line(next_next_line):
                    next_next_line = None
            if next_line:
                x_data.append(line)
                y_data.append(next_line)
            if last_line and next_line:
                x_data.append(last_line + make_split(last_line) + line)
                y_data.append(next_line)
            if next_line and next_next_line:
                x_data.append(line)
                y_data.append(next_line + make_split(next_line) + next_next_line)

    print(len(x_data), len(y_data))


    # # 构建问答对,进行测试
    # for ask, answer in zip(x_data[:-1], y_data[:-1]):
    #     print(''.join(ask))
    #     print(''.join(answer))
    #     print('-'*20)


    # 生成pkl文件备用
    data = list(zip(x_data, y_data))
    data = [
        (x, y) for x, y in data if limit > len(x) >= x_limit and limit > len(y) >= y_limit
    ]
    x_data, y_data = zip(*data)
    
    ## 训练以及词向量模型保存
    ws_input = WordSequence()
    ws_input.fit(x_data + y_data)
    print('dump')
    pickle.dump((x_data, y_data), open('chatbot_test.pkl', 'wb'))
    pickle.dump(ws_input, open('ws_test.pkl', 'wb'))
    print('done')




if __name__ == '__main__':
    main()
