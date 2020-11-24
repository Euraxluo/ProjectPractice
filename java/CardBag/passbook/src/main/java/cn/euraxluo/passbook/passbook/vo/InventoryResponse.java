package cn.euraxluo.passbook.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.vo
 * InventoryResponse
 * 2019/12/29 22:38
 * author:Euraxluo
 * 库存请求响应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    /** 用户 id */
    private Long userId;

    /** 优惠券模板信息 */
    private List<PassTemplateInfo> passTemplateInfos;
}
