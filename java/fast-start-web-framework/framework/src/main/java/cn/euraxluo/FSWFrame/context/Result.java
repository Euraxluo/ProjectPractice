package cn.euraxluo.FSWFrame.context;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * fast-start-web-framework
 * cn.euraxluo.FSWFrame.context
 * Result
 * 2019/11/24 18:54
 * author:Euraxluo
 * TODO
 */
public class Result {
    private Integer code;
    private Boolean status;
    private String message;

    @JSONField()
    private Object data;

    public Result() {
    }

    /**
     * @param code
     * @param status
     * @param data
     */
    public Result(Integer code, Boolean status, Object data) {
        this.code = code;
        this.status = status;
        this.data = data;
    }

    /**
     * @param code
     * @param status
     * @param message
     */
    public Result(Integer code, Boolean status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    /**
     * @param code
     * @param status
     * @param message
     * @param data
     */
    public Result(Integer code, Boolean status, String message, Object data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
