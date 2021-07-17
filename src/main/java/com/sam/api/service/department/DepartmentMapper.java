package com.sam.api.service.department;

import com.sam.api.db.entity.department.Department;
import com.sam.api.service.department.dto.DepartmentResponse;
import com.sam.api.service.department.dto.DepartmentUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentResponse mapToRes(Department department);

    void merge(@MappingTarget Department department, DepartmentUpdateRequest request);
}
