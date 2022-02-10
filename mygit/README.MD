## git初版源码学习

[reference](https://blog.csdn.net/weixin_35757732/article/details/112100833?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_title-1&spm=1001.2101.3001.4242#t4)



项目结构
```

$ tree -h
.
├── [2.4K]  cache.h
├── [ 503]  cat-file.c                  # 查看objects文件
├── [4.0K]  commit-tree.c               # 提交tree
├── [1.2K]  init-db.c                   # 初始化仓库
├── [ 970]  Makefile
├── [5.5K]  read-cache.c                # 读取当前索引文件内容
├── [8.2K]  README
├── [ 986]  read-tree.c                 # 读取tree
├── [2.0K]  show-diff.c                 # 查看diff内容
├── [5.3K]  update-cache.c              # 添加文件或目录
└── [1.4K]  write-tree.c                # 写入到tree

```

![image-20210405175459368](https://gitee.com/Euraxluo/images/raw/master/picgo/image-20210405175459368.png)

