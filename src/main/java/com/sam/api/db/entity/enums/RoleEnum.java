package com.sam.api.db.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    ROLE_SYSTEM_ADMIN,
    ROLE_DEPARTMENT_ADMIN,
    ROLE_DEPARTMENT_USER,
    ;

    @Override
    public String getAuthority() {
        return name();
    }
}
