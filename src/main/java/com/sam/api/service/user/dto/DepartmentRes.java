package com.sam.api.service.user.dto;

import com.sam.api.db.entity.department.DepartmentType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel
@Data
public class DepartmentRes {

    private Long id;
    private String name;
    private DepartmentType type;
}
