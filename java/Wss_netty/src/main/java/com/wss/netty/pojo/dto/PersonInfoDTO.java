package com.wss.netty.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

/**
 * Wss_netty
 * com.wss.netty.pojo.dto
 * PersonInfoDTO
 * 2019/10/20 0:00
 * author:Euraxluo
 * TODO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfoDTO {
    @NotBlank
    private String id;

    private String faceImageBig;
    private String faceImage;
    private String nickname;

}
