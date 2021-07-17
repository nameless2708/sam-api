package com.sam.api.service.department;

import com.sam.api.db.entity.department.Department;
import com.sam.api.db.repository.DepartmentRepository;
import com.sam.api.exception.SamNotFoundException;
import com.sam.api.service.department.dto.DepartmentResponse;
import com.sam.api.service.department.dto.DepartmentUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper mapper;

    /**
     * TODO Authorization?
     *
     * @param departmentId
     */
    public DepartmentResponse findById(Long departmentId) {
        Department department = this.departmentRepository
                .findById(departmentId)
                .orElseThrow(() -> new SamNotFoundException("Department not found"));
        return mapper.mapToRes(department);
    }

    /**
     * TODO Authorization?
     */
    public void update(Long departmentId, DepartmentUpdateRequest request) {
        Department department = this.departmentRepository
                .findById(departmentId)
                .orElseThrow(() -> new SamNotFoundException("Department not found"));
        mapper.merge(department, request);
        this.departmentRepository.save(department);
    }

}
