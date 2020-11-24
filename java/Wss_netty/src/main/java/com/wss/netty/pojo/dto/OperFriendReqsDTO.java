package com.wss.netty.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Wss_netty
 * com.wss.netty.pojo.dto
 * OperFriendReqsDTO
 * 2019/10/26 14:39
 * author:Euraxluo
 * TODO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperFriendReqsDTO {
    @NotBlank
    private String acceptUserId;
    @NotBlank
    private String sendUserId;
    @NotNull
    private Integer operType;

}