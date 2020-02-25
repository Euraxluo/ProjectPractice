package cn.euraxluo.passbook.merchants.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * merchants
 * cn.euraxluo.passbook.merchants.vo
 * CreateMerchantsResponse
 * 2019/12/28 10:07
 * author:Euraxluo
 * 创建商户响应对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMerchantsResponse {
    /** 商户id,创建失败则为-1*/
    private Integer id;
}
