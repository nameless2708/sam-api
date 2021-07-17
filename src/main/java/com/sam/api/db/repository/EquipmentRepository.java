package com.sam.api.db.repository;

import com.sam.api.db.entity.Equipment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends BaseRepository<Equipment, Long>,
        PagingAndSortingRepository<Equipment, Long> {
}
