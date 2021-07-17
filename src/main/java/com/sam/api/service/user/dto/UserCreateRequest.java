package com.sam.api.service.user.dto;

import com.sam.api.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@ApiModel
@Getter
@Setter
public class UserCreateRequest {
    @Size(min = User.MIN_USERNAME_LENGTH, max = User.MAX_USERNAME_LENGTH)
    private String username;

    @Size(min = User.MIN_PASSWORD_LENGTH, max = User.MAX_PASSWORD_LENGTH)
    private String password;

    private Long departmentId;
}
