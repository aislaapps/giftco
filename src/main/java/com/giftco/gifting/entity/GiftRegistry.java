package com.giftco.gifting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class GiftRegistry extends BaseEntity {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    //gift_format_id is foreign key to GiftFormat table

    //many to one is used because many gifts can have the same format
    @ManyToOne
    @JoinColumn(name="gift_format_id", nullable = false)
    private GiftFormats giftFormat;

    @Column(name="name", nullable = false)
    private String name;

    //if gift format its a cruise
    //departure date, default is null
    @Column(name="departure_date")
    private LocalDateTime departureDate;

    @Column(name = "debarkation_date")
    private LocalDateTime debarkationDate;

    //if gift format is a birthday party or similar
    @Column (name = "birth_date")
    private LocalDateTime birthDate;

    //expiry date (one year from now)
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    //many-to-many relationship with Accounts
    @ManyToMany
    @JoinTable(
            name = "registry_members",
            joinColumns = @JoinColumn(name = "gift_registry_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private List<Accounts> members;

    //admin of the gift registry
    @ManyToOne
    @JoinColumn(name = "admin_account")
    private Accounts adminAccount;
}
