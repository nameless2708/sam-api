package com.sam.api.service.demand;

import com.sam.api.db.entity.Demand;
import com.sam.api.db.entity.Demand_;
import com.sam.api.db.entity.enums.DemandStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public class DemandSpecification {
    public static Specification<Demand> departmentIdIn(Collection<Long> departmentIds) {
        return (root, query, criteriaBuilder)
                -> root.get(Demand_.DEPARTMENT_ID).in(departmentIds);
    }

    public static Specification<Demand> statusNot(DemandStatus status) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.notEqual(root.get(Demand_.STATUS), status);
    }

    public static Specification<Demand> statusNotIn(DemandStatus... statuses) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.not(root.get(Demand_.STATUS).in((Object[]) statuses));
    }
}
