package com.sam.api.service.report.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EquipmentReportDTO {
    private String item;
    private BigDecimal unitPrice;
    private Long quantity;
    private String subject;
    private String description;
    private String grade;

    public String getSubject(){
        return this.subject != null?this.subject : "";
    }

    public String getGrade(){
        return this.grade != null?this.grade : "";
    }
}
