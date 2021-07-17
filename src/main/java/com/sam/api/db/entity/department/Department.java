package com.sam.api.db.entity.department;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "departments")
@Data
@ToString
public class Department implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String name;

    @Column()
    @Enumerated(EnumType.STRING)
    private DepartmentType type;

    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne()
    @JoinColumn(name = "parent_id", referencedColumnName = "id", updatable = false, insertable = false)
    private Department parent;

    @Column(name = "tinh_thanh")
    private String tinhThanh;

    @Column(name = "quan_huyen")
    private String quanHuyen;

    @Column(name = "xa_phuong")
    private String xaPhuong;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "website")
    private String website;

    @Column(name = "tax")
    private String tax;

    @Column(name = "school_funding_type")
    @Enumerated(EnumType.STRING)
    private SchoolFundingType schoolFundingType;

    @Column(name = "school_level")
    @Enumerated(EnumType.STRING)
    private SchoolLevel schoolLevel;

    @Column(name = "school_principal")
    private String schoolPrincipal;

}
