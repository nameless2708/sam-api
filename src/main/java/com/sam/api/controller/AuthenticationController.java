package com.sam.api.controller;

import com.sam.api.db.entity.User;
import com.sam.api.service.auth.dto.LoginRequest;
import com.sam.api.service.auth.dto.LoginResponse;
import com.sam.api.service.user.dto.DepartmentRes;
import com.sam.api.service.user.dto.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Api(tags = "Authentication")
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @ApiOperation("Get Current Session")
    @PostMapping("session")
    public LoginResponse getCurrentSession(
            @AuthenticationPrincipal User user,
            HttpSession session) {
        DepartmentRes departmentRes = null;
        if (user.getDepartment() != null) {
            departmentRes = new DepartmentRes();
            departmentRes.setId(user.getDepartment().getId());
            departmentRes.setName(user.getDepartment().getName());
            departmentRes.setType(user.getDepartment().getType());
        }
        return new LoginResponse(session.getId(), new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRole(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                departmentRes
        ));
    }

    /**
     * This method is for docs purpose only
     *
     * @param loginRequest login request
     * @return login response
     */
    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return null;
    }

    /**
     * This method is for docs purpose only
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("logout")
    public void logout() {
    }
}
