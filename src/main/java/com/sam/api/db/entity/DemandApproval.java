package com.sam.api.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sam.api.db.entity.base.TrackTimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "demand_approvals")
@Getter
@Setter
public class DemandApproval extends TrackTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "demand_id")
    private Long demandId;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "message")
    private String message;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "demand_id", referencedColumnName = "id", insertable = false, updatable = false
    )
    private Demand demand;
}
