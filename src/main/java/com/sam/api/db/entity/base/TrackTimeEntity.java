package com.sam.api.db.entity.base;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TrackTimeEntity implements Serializable {

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    public Instant getUpdatedAt() {
        return updatedAt == null ? null : updatedAt.truncatedTo(ChronoUnit.SECONDS);
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt == null ? null : createdAt.truncatedTo(ChronoUnit.SECONDS);
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
