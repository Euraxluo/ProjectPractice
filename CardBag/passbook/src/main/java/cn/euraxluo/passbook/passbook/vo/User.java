package cn.euraxluo.passbook.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.vo
 * User
 * 2019/12/29 12:31
 * author:Euraxluo
 * User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /** 用户基本信息 */
    private BaseInfo baseInfo;
    /** 用户id */
    private Long id;
    /** 用户额外信息 */
    private OtherInfo otherInfo;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BaseInfo{
        /** 用户名*/
        private String name;
        /** 用户年龄*/
        private Integer age;
        /** 用户性别*/
        private String sex;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OtherInfo{
        /** 用户手机*/
        private String phone;
        /** 用户地址 */
        private String address;

    }
}
