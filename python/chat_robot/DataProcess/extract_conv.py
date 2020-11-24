# -*-coding: utf-8-*-
# @Author   : Euraxluo
# @Time     : 19-2-24 下午5:34
# @Software : PyCharm
import tensorflow as tf
import os

# 队列与队列管理器
# 1. 在训练样本的时候,希望数据有序读取
# tf.FIFOQueue(capacity,dtypes,name='fifo_queue') 先进先出队列,按顺序出队列
#   capacity:整数,可能存储在此队列中的元素数量的上限
#   dtypes:DType对象列表,长度dtypes必须等于每个队列元素中的张量数,dtype的元素形状,决定了后面进队元素的形状
# tf.RandomShuffleQueue 随机出队列

# 异步读取,队列管理器
# tf.train.QueueRunner(queue,enqueue_ops=None)创建一个QueueRunner


#线程协调器,实现一个简单的机制来协调一组线程的终止
# tf.train.Coordinator()
# - request_stop()
# - should_stop()检查是否要求停止
# - join(threads = None,stop_grace_period_secs=120)

#读取文件的流程
# 1.构建文件队列
# 将输出字符串到管道队列中
# tf.train.string_input_producer(string_tensor,shuffle = True)
#     - string_tensor  含有文件名的1阶张量
#     - num_epochs 过几遍数据,默认无线过数据
#     - return:具有输出字符串的队列


# 2.构造文件阅读器,读取队列内容(都是用read(file_queue)这个方法,从文件中读取指定数量内容)
# tf.TextLineReader(阅读文本文件csv,默认按行读取)

# tf.FixedLengthRecordReader(record_bytes):读取每个记录是固定数量的二进制文件
#     - record_bytes:整形,指定每次读取的字节数

# tf.TFRecordReader: 读取TFRecords文件

# 3.读取每个队列文件的每一个样本
# tf.decode_csv(records,record_defaults=None,field_delim=None,name=None)
#     - 将csv转换为张量,与tf.TextLineReader搭配使用
#     - records:tensor型字符串,每个字符串是csv中的记录行
#     - field_delim:默认分隔符","
#     - record_defauts:参数决定所得每一列张量的类型,并设置一个值,在输入字符串中缺少使用默认值

# tf.decode_raw(bytes,out_type,little_endian=None,name=None)
# 将字节转换为一个数字向量表示,字节为一字符类型的张量,二进制读取为uint8

# 4.批处理


def myqueu():
    # 1. 定义队列
    Q = tf.FIFOQueue(1000,tf.float32)
    # 2. 定义子线程操作
    var  = tf.Variable(0.0)
    data = tf.assign_add(var,tf.constant(1.0))
    enq = Q.enqueue(data)
    # 3. 定义队列管理器,指定线程数
    qr = tf.train.QueueRunner(Q,enqueue_ops=[enq]*4)
    #初始化变量
    init = tf.global_variables_initializer()
    with tf.Session() as sess:
        sess.run(init)
        #开启线程协调管理器
        coord = tf.train.Coordinator()
        threads = qr.create_threads(sess,coord=coord,start=True)
        for i in range(300):
            print(sess.run(Q.dequeue()))
        coord.request_stop()
        coord.join(threads)

def convread(filelist):
    # 1.构造队列
    file_queue = tf.train.string_input_producer(filelist)
    # 2.构造阅读器
    reader = tf.TextLineReader()
    key, value = reader.read(file_queue)
    # 3.对每一行解码
    records = [[" "],]#以字符串读取每一行,默认值是" "
    sentence = tf.decode_csv(value,record_defaults=records)
    #开启批处理
    sentence_batch = tf.train.batch([sentence],batch_size=30,num_threads=1,capacity=30)
    return sentence#返回句子

if __name__ == "__main__":
    #构造文件列表
    file_name = os.listdir("../data/")
    filelist = [os.path.join("../data/",file) for file in file_name]
    #读取文件
    sentence_batch = convread(filelist)
    #开启会话
    with tf.Session() as sess:
        #定义一个线程协调器
        coord = tf.train.Coordinator()
        #开启读取文件的线程
        threads = tf.train.start_queue_runners(sess,coord=coord)
        print(sess.run(sentence_batch))
        #回收子线程
        coord.request_stop()
        coord.join(threads)