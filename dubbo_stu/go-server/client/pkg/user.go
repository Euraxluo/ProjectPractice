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
	SayHi func(ctx context.Context, req []interface{}, rsp *User) error `dubbo:"SayHi"` //链接go server时,序列化方法名需要首字母大写
	//SayHi func(ctx context.Context, req []interface{}, rsp *User) error `dubbo:"sayHi"` //连接java时,这边序列化出来的方法名要首字母小写
}

func (u *UserProvider) Reference() string {
	return "UserProvider"
}

func (User) JavaClassName() string {
	return "com.ikurento.user.User"
}