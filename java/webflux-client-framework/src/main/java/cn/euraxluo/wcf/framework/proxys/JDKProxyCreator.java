package cn.euraxluo.wcf.framework.proxys;

import cn.euraxluo.wcf.framework.ApiServer;
import cn.euraxluo.wcf.framework.beans.MethodInfo;
import cn.euraxluo.wcf.framework.beans.ServerInfo;
import cn.euraxluo.wcf.framework.handlers.WebClientRestHandler;
import cn.euraxluo.wcf.framework.interfaces.ProxyCreator;
import cn.euraxluo.wcf.framework.interfaces.RestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * webflux-client-framework
 * cn.euraxluo.wcf.framework.proxys
 * JDKProxyCreator
 * 2019/11/29 21:01
 * author:Euraxluo
 * 使用JDK动态代理类实现代理接口
 */
@Slf4j
public class JDKProxyCreator implements ProxyCreator {
    @Override
    public Object createProxy(Class<?> type) {
        log.info("createProxy:"+type);
        /**
         * 根据接口得到API服务器的信息
         */
        ServerInfo serverInfo = extractServerInfo(type);
        log.info("serverInfo:"+serverInfo);
        /**
         *需要为每一个代理类一个实现
         */
        RestHandler handler = new WebClientRestHandler();
        //初始化
        handler.init(serverInfo);

        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{type}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //根据方法和参数得到调用信息

                        MethodInfo methodInfo = extractMethodInfo(method,args);
                        log.info("methodInfo:"+methodInfo);
                        /**
                         * 通过serverInfo 和 methodInfo 调用方法
                         */
                        return handler.invokeRest(methodInfo);
                    }

                    /**
                     * 根据方法定义和调用参数得到调用的相关信息
                     * @param method
                     * @param args
                     * @return
                     */
                    private MethodInfo extractMethodInfo(Method method, Object[] args) {
                        MethodInfo methodInfo = new MethodInfo();

                        extractUrlAndMethod(method,methodInfo);
                        extractParamsAndBody(method, args, methodInfo);
                        extractReturnInfo(method,methodInfo);

                        return methodInfo;
                    }

                    /**
                     * 提取返回对象信息
                     * @param method
                     * @param methodInfo
                     */
                    private void extractReturnInfo(Method method, MethodInfo methodInfo) {
                        //返回是否是flux
                        boolean isFlux =  method.getReturnType().isAssignableFrom(Flux.class);
                        methodInfo.setTypeFlux(isFlux);

                        //设置泛型的实际类型
                        Class<?> elementType = extractElementType(method.getGenericReturnType());
                        methodInfo.setElementType(elementType);
                    }

                    /**
                     * 得到泛型类型的实际类型
                     * @param genericReturnType
                     * @return
                     */
                    private Class<?> extractElementType(Type genericReturnType) {
                        Type[] types =  ((ParameterizedType)genericReturnType).getActualTypeArguments();
                        return (Class<?>) types[0];
                    }

                    /**
                     * 得到请求的param和body
                     * @param method
                     * @param args
                     * @param methodInfo
                     */
                    private void extractParamsAndBody(Method method, Object[] args, MethodInfo methodInfo) {
                        /**
                         * 得到调用的参数和body
                         */
                        Parameter[] parameters =  method.getParameters();
                        Map<String,Object> params =  new LinkedHashMap<>();
                        methodInfo.setParams(params);

                        for (int i =0 ;i<parameters.length;i++){
                            /**
                             * 判断是否带PathVariable
                             * 获取params
                             */
                            PathVariable pathVariable = parameters[i].getAnnotation(PathVariable.class);
                            if (pathVariable != null){
                                params.put(pathVariable.value(),args[i]);
                            }

                            /**
                             * 判断是否带RequestBody
                             * 获取body
                             */
                            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
                            if (requestBody != null){
                                methodInfo.setBody((Mono<?>) args[i]);
                                /**
                                 * 获取请求对象的实际类型
                                 */
                                methodInfo.setElementType(
                                        extractElementType(
                                                parameters[i].getParameterizedType())
                                );
                            }

                        }
                    }

                    /**
                     * 得到请求的URL和方法
                     * @param method
                     * @param methodInfo
                     */
                    private void extractUrlAndMethod(Method method, MethodInfo methodInfo) {
                        /**
                         * 得到uri和请求类型
                         */
//        MethodInfo methodInfo = new MethodInfo();
                        //得到请求URL和请求方法
                        Annotation[] annotations  = method.getAnnotations();
                        for (Annotation annotation:annotations){
                            //GET
                            if (annotation instanceof GetMapping){
                                GetMapping get = (GetMapping) annotation;
                                methodInfo.setUrl(get.value()[0]);
                                methodInfo.setMethod(HttpMethod.GET);
                            }
                            //POST
                            if (annotation instanceof PostMapping){
                                PostMapping post = (PostMapping) annotation;
                                methodInfo.setUrl(post.value()[0]);
                                methodInfo.setMethod(HttpMethod.POST);
                            }

                            //PUT
                            if (annotation instanceof PutMapping){
                                PutMapping put = (PutMapping) annotation;
                                methodInfo.setUrl(put.value()[0]);
                                methodInfo.setMethod(HttpMethod.PUT);
                            }

                            //DELETE
                            if (annotation instanceof DeleteMapping){
                                DeleteMapping delete = (DeleteMapping) annotation;
                                methodInfo.setUrl(delete.value()[0]);
                                methodInfo.setMethod(HttpMethod.DELETE);
                            }
                        }
                    }


                });
    }

    /**
     * 提取服务器信息
     * @param type
     * @return
     */
    private ServerInfo extractServerInfo(Class<?> type) {
        /**
         * 设置服务器 url
         */
        ServerInfo serverInfo = new ServerInfo();
        ApiServer apiServer = type.getAnnotation(ApiServer.class);
        serverInfo.setUrl(apiServer.value());
        return serverInfo;
    }

}
