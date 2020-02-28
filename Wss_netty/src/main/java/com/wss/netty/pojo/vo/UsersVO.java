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
public class UsersVO {
    @NotBlank
    private String id;
    @NotBlank
    private String username;
    @NotBlank
    private String nickname;
    private String faceImage;
    private String faceImageBig;
    private String qrcode;

}