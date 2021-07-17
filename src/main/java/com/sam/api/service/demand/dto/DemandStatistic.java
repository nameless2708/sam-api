package com.sam.api.service.demand.dto;

import com.sam.api.db.entity.enums.DemandStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Map;

@ApiModel
@Data
public class DemandStatistic {
    private Map<DemandStatus, Long> data;
}
