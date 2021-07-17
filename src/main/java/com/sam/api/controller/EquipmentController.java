package com.sam.api.controller;

import com.sam.api.common.PaginateRes;
import com.sam.api.db.entity.User;
import com.sam.api.service.equipment.EquipmentService;
import com.sam.api.service.equipment.dto.EquipmentCreateReq;
import com.sam.api.service.equipment.dto.EquipmentQuery;
import com.sam.api.service.equipment.dto.EquipmentRes;
import com.sam.api.service.equipment.dto.EquipmentUpdateReq;
import com.sam.api.service.report.JasperReportService;
import io.swagger.annotations.Api;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Equipment")
@RestController
@RequestMapping("equipments")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private JasperReportService jasperReportService;

    @GetMapping
    public PaginateRes<EquipmentRes> index(@AuthenticationPrincipal User user , EquipmentQuery query) {
        return equipmentService.index(user, query);
    }

    @PostMapping()
    public void create(@AuthenticationPrincipal User user, @Valid @RequestBody EquipmentCreateReq req) {
        this.equipmentService.create(user, req);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody EquipmentUpdateReq req) {
        this.equipmentService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.equipmentService.delete(id);
    }

    @GetMapping("/export")
    public FileSystemResource export() throws JRException {
        return new FileSystemResource(this.jasperReportService.exportPdf());
    }
}
