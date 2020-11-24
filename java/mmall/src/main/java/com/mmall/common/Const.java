package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @ClassName Const
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-7 下午5:43
 */
public class Const {
    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface ProductListOrderBy{//使用set提高效率
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
        //与前端约定使用下划线
    }

    public interface Cart{
        int CHECKED = 1;//购物车选中状态
        int UN_CHECKED = 0;//购物车未选中状态
        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }
    //通过内部接口类来把常量分组,没有枚举类重,而且里面还是常量
    public interface Role{
        int ROLE_CUSTOMER = 0;//普通用户
        int ROLE_ADMIN = 1;//管理员
    }

    public enum ProductStatusEnum{
        ON_SALE(1,"在售");
        private String value;
        private int code;

        ProductStatusEnum(int code,String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
    public enum OrderStatusEnum{
        CANCELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已付款"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单完成"),
        ORDER_CLOSE(60,"订单关闭");

        private String value;
        private int code;
        OrderStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        public String getValue(){
            return value;
        }
        public int getCode(){
            return code;
        }
        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum : values()){
                if(orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的支付状态");
        }
    }
    public interface AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }
    public enum PayPlatfromEnum{
        AlIPAY(1,"支付宝");

        private String value;
        private int code;
        PayPlatfromEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        public String getValue(){
            return value;
        }
        public int getCode(){
            return code;
        }
    }
    public enum PaymentTypeEnum{
        ONLINE_PAY(1,"在线支付");

        private String value;
        private int code;
        PaymentTypeEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        public String getValue(){
            return value;
        }
        public int getCode(){
            return code;
        }

        public static PaymentTypeEnum codeOf(int code){
            for (PaymentTypeEnum paymentTypeEnum : values()){
                if(paymentTypeEnum.getCode() == code){
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到对应的支付方式");
        }
    }
}
