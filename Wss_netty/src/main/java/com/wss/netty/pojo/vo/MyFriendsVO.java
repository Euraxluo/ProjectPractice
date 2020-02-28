package com.wss.netty.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyFriendsVO {
    @NotBlank
    private String fId;
    @NotBlank
    private String fUsername;
    @NotBlank
    private String fNickname;
    private String fFaceImage;
}