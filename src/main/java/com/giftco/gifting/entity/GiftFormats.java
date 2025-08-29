package com.giftco.gifting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter
@Setter @AllArgsConstructor @NoArgsConstructor
public class GiftFormats {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="gift_format_id")
    private Long giftFormatId;
    @Column(name="format_name", unique = true, nullable = false)
    private String giftFormatName;
}
