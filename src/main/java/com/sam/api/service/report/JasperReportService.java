package com.sam.api.service.report;

import com.sam.api.db.entity.Equipment;
import com.sam.api.db.repository.EquipmentRepository;
import com.sam.api.service.report.dto.EquipmentReportDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JasperReportService {

    @Autowired
    EquipmentRepository equipmentRepository;

    public String exportPdf() throws JRException {
        List<Equipment> equipmentList = equipmentRepository.findAll();
        InputStream reportTmpl
                = getClass().getResourceAsStream("/report/table-demo-2.jrxml");
        JasperReport jreport = JasperCompileManager.compileReport(reportTmpl);
        jreport.setProperty("net.sf.jasperreports.default.pdf.encoding", "Cp1250");
        Set<EquipmentReportDTO> equipmentDemands = new HashSet<>();
        equipmentDemands = equipmentList.stream().map(
                equipment -> {
                    EquipmentReportDTO equipmentReportDTO = new EquipmentReportDTO();
                    equipmentReportDTO.setItem(equipment.getName());
                    equipmentReportDTO.setUnitPrice(equipment.getPrice());
                    equipmentReportDTO.setQuantity(equipment.getQuantity());
                    equipmentReportDTO.setGrade(equipment.getGrade());
                    equipmentReportDTO.setSubject(equipment.getSubject());
                    return equipmentReportDTO;
                }
        ).collect(Collectors.toSet());
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(equipmentDemands);
        Map params = new HashMap();
        params.put("datasource", ds);
        JasperPrint jprint = JasperFillManager.fillReport(jreport,
                params, new JREmptyDataSource());
        String uuid = UUID.randomUUID().toString();
        JasperExportManager.exportReportToPdfFile(jprint,
                "/tmp/" + uuid + "-table.pdf");
        return "/tmp/" + uuid + "-table.pdf";
    }
}
