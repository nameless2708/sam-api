package com.sam.api.service.equipment;

import com.sam.api.db.entity.Equipment;
import com.sam.api.service.equipment.dto.EquipmentRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {

    EquipmentRes mapEntityToRes(Equipment equipment);
}
