package com.sam.api.db.entity;

import com.sam.api.db.entity.base.TrackTimeEntity;
import com.sam.api.db.entity.department.Department;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "equipments")
@Getter
@Setter
public class Equipment extends TrackTimeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Long quantity;

    @Column
    private String subject;

    @Column
    private String description;

    @Column
    private String grade;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;
}
