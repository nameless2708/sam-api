package com.sam.api.db.repository;

import com.sam.api.db.entity.department.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends BaseRepository<Department, Long> {
    Optional<Department> findByParentId(Long parentId);

    @Query("SELECT d.id from departments d where d.parentId in :ids")
    List<Long> findAllByParentIdIn(@Param("ids") Collection<Long> ids);
}
