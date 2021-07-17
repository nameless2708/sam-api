package com.sam.api.service.equipment;

import com.sam.api.db.entity.Equipment;
import com.sam.api.db.entity.Equipment_;
import org.springframework.data.jpa.domain.Specification;

public class EquipmentSpecification {

    public static Specification<Equipment> nameLike(String name) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get(Equipment_.NAME), "%" + name + "%");
    }
}
