package com.sam.api.service.demand.dto;

import com.sam.api.db.entity.DemandApproval;
import com.sam.api.db.entity.enums.DemandStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@ApiModel
@Getter
@Setter
public class DemandDetailResponse {
    private Long id;
    private DemandResponse.Department department;
    private DemandResponse.Department departmentLevel1;
    private DemandResponse.Department departmentLevel2;
    private String description;
    private DemandStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private Set<Equipment> equipments;
    private Set<DemandApproval> demandApprovals;

    @ApiModel
    @Data
    public static class Equipment {
        private Long id;
        private String name;
        private Long quantity;
    }
}
