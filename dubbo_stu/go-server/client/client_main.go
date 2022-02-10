package main

import (
	"context"
	gxlog "github.com/dubbogo/gost/log"
	"os"
	"time"
)

import (
	hessian "github.com/apache/dubbo-go-hessian2"
	"go-server/client/pkg"
)

import (
	_ "github.com/apache/dubbo-go/cluster/cluster_impl"
	_ "github.com/apache/dubbo-go/cluster/loadbalance"
	_ "github.com/apache/dubbo-go/common/proxy/proxy_factory"
	"github.com/apache/dubbo-go/config"
	_ "github.com/apache/dubbo-go/filter/filter_impl"
	_ "github.com/apache/dubbo-go/protocol/dubbo"
	_ "github.com/apache/dubbo-go/registry/protocol"
	_ "github.com/apache/dubbo-go/registry/zookeeper"
)

var userProvider = new(pkg.UserProvider)

func init() {
	config.SetConsumerService(userProvider)
	hessian.RegisterPOJO(&pkg.User{})
}

// need to setup environment variable "CONF_CONSUMER_FILE_PATH" to "conf/client.yml" before run
func main() {
	//config.SetConsumerService(userProvider)
	//hessian.RegisterPOJO(&pkg.User{})
	hessian.RegisterPOJO(&pkg.User{})
	config.Load()
	time.Sleep(3 * time.Second)

	gxlog.CInfo("\n\n\nstart to test dubbo")
	user := &pkg.User{}
	err := userProvider.GetUser(context.TODO(), []interface{}{"A001"}, user)


	if err != nil {
		gxlog.CError("error: %v\n", err)
		os.Exit(1)
		return
	}
	gxlog.CInfo("response result: %v\n", user)


	err = userProvider.SayHi(context.TODO(), []interface{}{"A002","USER NAME",100}, user)
	if err != nil {
		gxlog.CError("error: %v\n", err)
		os.Exit(1)
		return
	}
	gxlog.CInfo("response result: %v\n", user)

}
