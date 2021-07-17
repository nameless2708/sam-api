package com.sam.api.service.equipment.dto;

import com.sam.api.common.PaginateQuery;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class EquipmentQuery extends PaginateQuery {
    private String search;
}
