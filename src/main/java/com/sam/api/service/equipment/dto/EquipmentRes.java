package com.sam.api.service.equipment.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.Instant;

@ApiModel
@Data
public class EquipmentRes {
    private int id;
    private String name;
    private String price;
    private int quantity;
    private String subject;
    private String description;
    private String grade;
    private Instant createdAt;
    private Instant updatedAt;
}

