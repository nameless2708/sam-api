package com.sam.api.db.repository;

import com.sam.api.db.entity.EquipmentDemand;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentDemandRepository extends BaseRepository<EquipmentDemand, EquipmentDemand.EquipmentDemandId> {

    void deleteByDemandId(Long demandId);
}
