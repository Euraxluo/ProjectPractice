package cn.euraxluo.passbook.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.vo
 * ErrorInfo
 * 2019/12/29 15:39
 * author:Euraxluo
 *统一的错误信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo<T> {
    /** 错误码 */
    public static final Integer ERROR = -1;

    /** 特定错误码 */
    private Integer code;

    /** 错误信息 */
    private String message;

    /** 请求 url */
    private String url;

    /** 请求返回的数据 */
    private T data;
}
