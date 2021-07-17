package com.sam.api.service.auth.dto;

import com.sam.api.service.user.dto.UserResponse;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class LoginResponse {
    private String token;
    private UserResponse user;

    public LoginResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }
}
