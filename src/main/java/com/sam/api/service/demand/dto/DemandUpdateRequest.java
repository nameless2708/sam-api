package com.sam.api.service.demand.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel
@Data
public class DemandUpdateRequest {
    @NotNull
    @Size(min = 1, max = 50)
    private List<@Valid Equipment> equipments;

    private String description;

    @ApiModel
    @Data
    public static class Equipment {
        @NotNull
        private Long id;
        @Min(0)
        private Long quantity;
    }
}
