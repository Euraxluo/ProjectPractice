package cn.euraxluo.wcf.framework.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * webflux-client-framework
 * cn.euraxluo.wcf.framework.beans
 * MethodInfo
 * 2019/11/29 21:08
 * author:Euraxluo
 * TODO
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodInfo {
    /**
     * 请求url
     */
    private String url;
    /**
     * 请求方法
     */
    private HttpMethod method;
    /**
     * 请求参数
     */
    private Map<String,Object> params;
    /**
     * 请求body
     */
    private Mono body;

    /**
     * 请求Body的类型
     */
    private Class<?> bodyElementType;

    /**
     * 返回是Flux还是Mono
     */
    private boolean typeFlux;
    /**
     * 返回对象的类型
     */
    private Class<?> ElementType;
}
