package com.giftco.gifting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter @ToString
public class BaseEntity {

    // common fields:

    // created date
    @Column(updatable = false)
    private LocalDateTime createdAt;
    // last modified date
    @Column(insertable = false)
    private LocalDateTime updatedAt;
    // created by
    @Column(insertable = false)
    private String createdBy;
    // last modified by
    @Column(insertable = false)
    private String updatedBy;
}
