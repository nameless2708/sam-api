package com.sam.api.service.department.dto;

import com.sam.api.db.entity.department.DepartmentType;
import com.sam.api.db.entity.department.SchoolFundingType;
import com.sam.api.db.entity.department.SchoolLevel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel
@Data
public class DepartmentUpdateRequest {
    private String name;
    private DepartmentType type;
    private Long parentId;
    private String tinhThanh;
    private String quanHuyen;
    private String xaPhuong;
    private String address;
    private String email;
    private String phone;
    private String website;
    private String tax;
    private SchoolFundingType schoolFundingType;
    private SchoolLevel schoolLevel;
    private String schoolPrincipal;
}
