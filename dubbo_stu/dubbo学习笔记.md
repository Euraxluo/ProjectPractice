dubbo 学习笔记



### 配置项目

#### 环境变量

目前 dubbo-go 有 3 个环境变量需要配置。

- `CONF_CONSUMER_FILE_PATH` : Consumer 端配置文件路径，使用 consumer 时必需。
- `CONF_PROVIDER_FILE_PATH`：Provider 端配置文件路径，使用 provider 时必需。
- `APP_LOG_CONF_FILE` ：Log 日志文件路径，必需。
- `CONF_ROUTER_FILE_PATH`：File Router 规则配置文件路径，使用 File Router 时需要。



#### reference

1.id

```go
type UserProvider struct {
	GetUser func(ctx context.Context, req []interface{}, rsp *User) error
	SayHi func(ctx context.Context, req []interface{}, rsp *User) error `dubbo:"SayHi"` //链接go server时,序列化方法名需要首字母大写
	//SayHi func(ctx context.Context, req []interface{}, rsp *User) error `dubbo:"sayHi"` //连接java时,这边序列化出来的方法名要首字母小写
}

func (u *UserProvider) Reference() string {
	return "UserProvider"
}
```

上面Reference返回的值需要和配置文件中

```yml
references:
  "UserProvider":
```

的id名一致



2. interface

    ```yml
    references:
      "UserProvider":
        registry: "demoZk"
        protocol: "dubbo"
        interface: "com.ikurento.user.UserProvider"
    ```

    

其中 interface,就是java服务的包名,当调用go服务时,需要和服务端的配置文件中此处的值一致



3.methods 可以省略

```yml
references:
  "UserProvider":
    registry: "demoZk"
    protocol: "dubbo"
    interface: "com.ikurento.user.UserProvider"
    cluster: "failover"
#    methods:
#      - name: "GetUser"
#        retries: 3
#      - name: "sayHi"
#        retries: 3
```

4.register项目可以省略,省略时,向所有注册中心注册

```yml
references:
  "UserProvider":
    interface: "com.ikurento.user.UserProvider"
    protocol: "dubbo"
    cluster: "failover"
```





#### register

```yml
registries:
  "demoZk":
    protocol: "zookeeper"
    timeout: "3s"
    address: "127.0.0.1:2181" #"172.16.120.181:2181,172.16.120.182:2181"
    username: ""
    password: ""
```

address项,可以通过逗号分割





### 接口和bean的配置

```go
package pkg

import (
	"context"
	"time"
)

type User struct {
	Id   string
	Name string
	Age  int32
	Time time.Time
}
type UserProvider struct {
	GetUser func(ctx context.Context, req []interface{}, rsp *User) error
	//SayHi func(ctx context.Context, req []interface{}, rsp *User) error `dubbo:"SayHi"` //链接go server时,序列化方法名需要首字母大写
	SayHi func(ctx context.Context, req []interface{}, rsp *User) error `dubbo:"sayHi"` //连接java时,这边序列化出来的方法名要首字母小写
}

func (u *UserProvider) Reference() string {
	return "UserProvider"
}

func (User) JavaClassName() string {
	return "com.ikurento.user.User"
}
```

在UserProvider这个服务中,每一个接口可以通过`dubbo:"sayHi"` 来序列化为小写字母开头

