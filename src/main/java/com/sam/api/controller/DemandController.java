package com.sam.api.controller;

import com.sam.api.common.PaginateRes;
import com.sam.api.db.entity.User;
import com.sam.api.service.approval.DemandApprovalService;
import com.sam.api.service.approval.dto.DemandApprovalCreateReq;
import com.sam.api.service.demand.DemandService;

import com.sam.api.service.demand.dto.*;
import com.sam.api.service.report.JasperReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Equipment Demand")
@RestController
@RequestMapping("demands")
public class DemandController {

    private final DemandService demandService;

    private final DemandApprovalService demandApprovalService;

    @Autowired
    private JasperReportService jasperReportService;

    public DemandController(DemandService demandService, DemandApprovalService demandApprovalService) {
        this.demandService = demandService;
        this.demandApprovalService = demandApprovalService;
    }

    @ApiOperation("Create Equipment Request")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @Valid @RequestBody DemandCreateRequest request,
            @AuthenticationPrincipal User user
    ) {
        this.demandService.create(user, request);
    }

    @ApiOperation("Get List Equipment Demand")
    @GetMapping()
    public PaginateRes<DemandResponse> index(@AuthenticationPrincipal User user, DemandQuery query) {
        return this.demandService.index(user, query);
    }

    @GetMapping("statistic")
    public DemandStatistic statistic(@AuthenticationPrincipal User user) {
        return this.demandService.statistic(user);
    }

    @ApiOperation("Get Equipment Demand By Id")
    @GetMapping("/{id}")
    public DemandDetailResponse findById(
            @AuthenticationPrincipal User user,
            @PathVariable("id") Long demandId) {
        return this.demandService.findById(user, demandId);
    }

    @ApiOperation("Edit Equipment Demand By Id")
    @PutMapping("/{id}")
    public void update(@AuthenticationPrincipal User user,
                       @PathVariable("id") Long demandId,
                       @Valid @RequestBody DemandUpdateRequest request) {
        this.demandService.update(user, demandId, request);
    }

    @PostMapping("/{id}/approval")
    public void approval(
            @PathVariable("id") Long demandId,
            @Valid @RequestBody DemandApprovalCreateReq req,
            @AuthenticationPrincipal User user) {
        this.demandApprovalService.create(user, demandId, req);
    }

    @PostMapping("/{id}/submit")
    public void submit(@AuthenticationPrincipal User user, @PathVariable("id") Long demandId) {
        this.demandService.submit(user, demandId);
    }

    @PostMapping("/{id}/confirm")
    public void confirm(@AuthenticationPrincipal User user, @PathVariable("id") Long demandId) {
        this.demandService.confirm(user, demandId);
    }

    @DeleteMapping("/{id}")
    public void delete(@AuthenticationPrincipal User user, @PathVariable("id") Long demandId) {
        this.demandService.delete(user, demandId);
    }

}
