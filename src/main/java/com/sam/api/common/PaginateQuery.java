package com.sam.api.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ApiModel
@Data
public class PaginateQuery {

    @ApiModelProperty(value = "Number of items per page")
    private int size = 20;

    @ApiModelProperty(value = "Get specific page")
    private int page;

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
