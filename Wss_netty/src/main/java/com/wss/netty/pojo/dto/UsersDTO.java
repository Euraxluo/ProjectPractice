package com.wss.netty.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Wss_netty
 * com.wss.netty.pojo.dto
 * UsersDTO
 * 2019/10/15 13:10
 * author:Euraxluo
 * TODO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
//    @NotBlank
    private String cid;
}
