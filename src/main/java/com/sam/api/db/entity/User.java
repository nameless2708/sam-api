package com.sam.api.db.entity;

import com.sam.api.db.entity.base.TrackTimeEntity;
import com.sam.api.db.entity.department.Department;
import com.sam.api.db.entity.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends TrackTimeEntity implements UserDetails {

    public static final int MIN_USERNAME_LENGTH = 5;
    public static final int MAX_USERNAME_LENGTH = 20;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @ManyToOne()
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @Override
    public boolean isAccountNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isEnabled() {
        return this.deletedAt == null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(this.role);
    }
}
