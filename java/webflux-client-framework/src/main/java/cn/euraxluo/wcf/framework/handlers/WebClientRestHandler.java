package cn.euraxluo.wcf.framework.handlers;

import cn.euraxluo.wcf.framework.beans.MethodInfo;
import cn.euraxluo.wcf.framework.beans.ServerInfo;
import cn.euraxluo.wcf.framework.interfaces.RestHandler;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * webflux-client-framework
 * cn.euraxluo.wcf.framework.handlers
 * WebClientRestHandler
 * 2019/11/29 21:46
 * author:Euraxluo
 * TODO
 */
public class WebClientRestHandler implements RestHandler {

    private WebClient client;
    private WebClient.RequestBodySpec request;

    /**
     * 处理Rest请求
     * @param methodInfo
     * @return
     */
    @Override
    public Object invokeRest(MethodInfo methodInfo) {
        Object res = null;
        request = this.client
                .method(methodInfo.getMethod())//请求方法
                .uri(methodInfo.getUrl(),methodInfo.getParams())//请求uri
                .accept(MediaType.APPLICATION_JSON);//mediaType

        WebClient.ResponseSpec retrieve = null;

        if (methodInfo.getBody() != null){
            retrieve = request.body(methodInfo.getBody(),methodInfo.getBodyElementType())
                            .retrieve();//发出请求
        }else {
            retrieve = request.retrieve();
        }

        //异常处理
        retrieve.onStatus(httpStatus -> httpStatus.value() == 404,
                clientResponse -> Mono.just(new RuntimeException("NOT FOUND"))
                );
        //判断body是flux还是mono
        if (methodInfo.isTypeFlux()){
            res = retrieve.bodyToFlux(methodInfo.getElementType());
        }else {
            res = retrieve.bodyToMono(methodInfo.getElementType());
        }
        return res;
    }

    /**
     * 初始化服务器信息
     * @param serverInfo
     */
    @Override
    public void init(ServerInfo serverInfo) {
        this.client = WebClient.create(serverInfo.getUrl());
    }
}
