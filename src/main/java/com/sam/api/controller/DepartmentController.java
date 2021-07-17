package com.sam.api.controller;

import com.sam.api.service.department.DepartmentService;
import com.sam.api.service.department.dto.DepartmentResponse;
import com.sam.api.service.department.dto.DepartmentUpdateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Department")
@RestController
@RequestMapping("departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation("Get Department By Id")
    @GetMapping("/{id}")
    DepartmentResponse findById(@PathVariable("id") Long id) {
        return this.departmentService.findById(id);
    }

    @ApiOperation("Edit Department By Id")
    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id,
                       @Valid @RequestBody DepartmentUpdateRequest request) {
        this.departmentService.update(id, request);
    }
}
