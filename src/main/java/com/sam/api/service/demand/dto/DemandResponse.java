package com.sam.api.service.demand.dto;

import com.sam.api.db.entity.enums.DemandStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.Instant;

@ApiModel
@Data
public class DemandResponse {
    private Long id;
    private Department department;
    private Department departmentLevel1;
    private Department departmentLevel2;
    private String description;
    private DemandStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    @ApiModel
    @Data
    public static class Department {
        private Long id;
        private String name;
    }
}
