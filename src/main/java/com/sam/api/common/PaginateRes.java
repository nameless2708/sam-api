package com.sam.api.common;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.Collection;

@ApiModel
@Getter
public class PaginateRes<E> {
    private final Collection<E> data;

    private final int currentPage;

    private final int lastPage;

    private final int perPage;

    private final long total;

    public PaginateRes(Collection<E> data, int currentPage, int lastPage, int perPage, long total) {
        this.data = data;
        this.currentPage = currentPage;
        this.lastPage = lastPage;
        this.perPage = perPage;
        this.total = total;
    }

    public PaginateRes(Page<E> page) {
        this.data = page.getContent();
        this.currentPage = page.getNumber();
        this.lastPage = page.getTotalPages() == 0 ? 0 : page.getTotalPages() - 1;
        this.perPage = page.getSize();
        this.total = page.getTotalElements();
    }
}
