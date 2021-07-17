package com.sam.api.service.equipment.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@ApiModel
@Data
public class EquipmentUpdateReq {
    @NotEmpty
    private String name;
    private BigDecimal price;
    private Long quantity;
    private String subject;
    private String description;
    private String grade;
}
