package com.sam.api.service.user.dto;

import com.sam.api.db.entity.enums.RoleEnum;
import io.swagger.annotations.ApiModel;
import lombok.Getter;

import java.time.Instant;

@ApiModel
@Getter
public class UserResponse {

    private final Long id;
    private final String name;
    private final String username;
    private final RoleEnum role;
    private final String email;
    private final String phone;
    private final String address;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final DepartmentRes department;

    public UserResponse(Long id,
                        String name,
                        String username,
                        RoleEnum role,
                        String email,
                        String phone,
                        String address,
                        Instant createdAt,
                        Instant updatedAt, DepartmentRes department) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.department = department;
    }
}
