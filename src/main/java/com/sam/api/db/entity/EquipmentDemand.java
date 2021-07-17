package com.sam.api.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(EquipmentDemand.EquipmentDemandId.class)
@Table(name = "equipment_demand")
@Getter
@Setter
public class EquipmentDemand implements Serializable {
    @Id
    @Column(name = "equipment_id")
    private Long equipmentId;

    @Id
    @Column(name = "demand_id")
    private Long demandId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "demand_id", referencedColumnName = "id", insertable = false, updatable = false
    )
    private Demand demand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "equipment_id", referencedColumnName = "id", insertable = false, updatable = false
    )
    private Equipment equipment;

    @Column(name = "quantity")
    private Long quantity;

    @Getter
    @Setter
    public static class EquipmentDemandId implements Serializable {
        private Long equipmentId;
        private Long demandId;

        public EquipmentDemandId() {
        }

        public EquipmentDemandId(Long equipmentId, Long demandId) {
            this.equipmentId = equipmentId;
            this.demandId = demandId;
        }
    }
}
