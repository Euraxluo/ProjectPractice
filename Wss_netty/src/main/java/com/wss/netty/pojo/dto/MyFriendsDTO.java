package com.wss.netty.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Wss_netty
 * com.wss.netty.pojo.dto
 * MyFriendsDTO
 * 2019/10/24 9:47
 * author:Euraxluo
 * TODO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyFriendsDTO {
    @NotBlank
    private String myUserId;
    @NotBlank
    private String myFrendUserName;
}
