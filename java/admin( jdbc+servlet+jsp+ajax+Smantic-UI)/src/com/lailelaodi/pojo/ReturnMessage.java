package com.lailelaodi.pojo;

/**
 * @ClassName ReturnMessage
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-25 上午11:21
 */
public class ReturnMessage {
    private  int code;
    private String message;
    private Object data;

    @Override
    public String toString() {
        return "ReturnMessage{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    private ReturnMessage() {
        super();
    }

    private ReturnMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }
    private ReturnMessage(int code,Object data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public static  ReturnMessage createBySuccessMessage(String msg){
        return new ReturnMessage(1,msg);
    }
    public static  ReturnMessage createBySuccess(Object data){
        return new ReturnMessage(1,data);
    }

    public static  ReturnMessage createByErrorMessage(String errorMessage){
        return new ReturnMessage(0,errorMessage);
    }
    public static  ReturnMessage createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ReturnMessage(errorCode,errorMessage);
    }
}
