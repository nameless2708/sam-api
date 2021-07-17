package com.sam.api.controller;

import com.sam.api.service.user.UserService;
import com.sam.api.service.user.dto.UserCreateRequest;
import com.sam.api.service.user.dto.UserResponse;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "User")
@RestController("users")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserResponse index() {
        return this.userService.findById(1);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserCreateRequest request) {
        this.userService.create(request);
    }
}
