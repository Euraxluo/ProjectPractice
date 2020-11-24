package cn.euraxluo.wcf.framework.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * webflux-client-framework
 * cn.euraxluo.wcf.framework.beans
 * ServerInfo
 * 2019/11/29 21:03
 * author:Euraxluo
 * 服务器信息类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfo {
    /**
     * 服务器url
     */
    private String url;
}
