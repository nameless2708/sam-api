package com.sam.api.service.approval.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel
@Data
public class DemandApprovalCreateReq {
    private String message;
    private boolean approved;
}
