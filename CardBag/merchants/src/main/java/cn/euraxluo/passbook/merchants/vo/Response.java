package cn.euraxluo.passbook.merchants.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * merchants
 * cn.euraxluo.passbook.merchants.vo
 * Response
 * 2019/12/27 13:41
 * author:Euraxluo
 * 响应对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    /** 错误码,正确返回 0 */
    private Integer errorCode = 0;

    /** 错误信息,正确返回空字符串 */
    private String errorMsg = "";

    /** 返回值对象 */
    private Object data;

    /**
     * 正确的响应构造函数
     * @param data
     */
    public Response(Object data) {
        this.data = data;
    }
}
