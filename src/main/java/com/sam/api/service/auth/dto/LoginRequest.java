package com.sam.api.service.auth.dto;

import com.sam.api.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Size;

@ApiModel
@Data
public class LoginRequest {

    @Size(min = User.MIN_USERNAME_LENGTH, max = User.MAX_USERNAME_LENGTH)
    private String username;

    @Size(min = User.MIN_PASSWORD_LENGTH, max = User.MAX_PASSWORD_LENGTH)
    private String password;
}
