package com.sam.api.db.repository;

import com.sam.api.db.entity.Demand;
import com.sam.api.db.entity.enums.DemandStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface DemandRepository extends BaseRepository<Demand, Long>, PagingAndSortingRepository<Demand, Long> {
    Page<Demand> findByDepartmentId(Specification specification, Long departmentId, Pageable pageable);

    Page<Demand> findByDepartmentIdAndStatusIn(Long departmentId, Set<DemandStatus> statuses, Pageable pageable);

    @Query(value = "select count(d) as count, d.status as status from Demand d where d.departmentId in :departmentIds group by d.status")
    List<StatusStatistic> countGroupByStatus(@Param("departmentIds") Collection<Long> departmentIds);

    interface StatusStatistic {
        Long getCount();

        DemandStatus getStatus();
    }
}
