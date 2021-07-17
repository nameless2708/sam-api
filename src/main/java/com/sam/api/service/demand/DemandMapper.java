package com.sam.api.service.demand;

import com.sam.api.db.entity.Demand;
import com.sam.api.db.entity.EquipmentDemand;
import com.sam.api.service.demand.dto.DemandDetailResponse;
import com.sam.api.service.demand.dto.DemandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DemandMapper {

    @Mappings({
            @Mapping(source = "department.parent", target = "departmentLevel2"),
            @Mapping(source = "department.parent.parent", target = "departmentLevel1"),
    })
    DemandResponse mapToRes(Demand demand);

    @Mappings({
            @Mapping(source = "equipmentDemands", target = "equipments"),
            @Mapping(source = "department.parent", target = "departmentLevel2"),
            @Mapping(source = "department.parent.parent", target = "departmentLevel1"),
    })
    DemandDetailResponse mapToResDetail(Demand demand);

    @Mappings({
            @Mapping(source = "equipment.name", target = "name"),
            @Mapping(source = "equipmentId", target = "id")
    })
    DemandDetailResponse.Equipment map(EquipmentDemand equipmentDemand);

    List<DemandDetailResponse.Equipment> map(List<EquipmentDemand> equipmentDemands);
}
