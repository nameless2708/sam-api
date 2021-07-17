package com.sam.api.db.entity;

import com.sam.api.db.entity.base.TrackTimeEntity;
import com.sam.api.db.entity.department.Department;
import com.sam.api.db.entity.enums.DemandStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "demands")
@Getter
@Setter
public class Demand extends TrackTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String description;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DemandStatus status;

    @Column(name = "department_id")
    private Long departmentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Department department;

    @OneToMany(mappedBy = "demand", fetch = FetchType.LAZY)
    private Set<EquipmentDemand> equipmentDemands = new HashSet<>();

    @OneToMany(mappedBy = "demand", fetch = FetchType.LAZY)
    private Set<DemandApproval> demandApprovals = new HashSet<>();
}
