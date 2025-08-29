package com.giftco.gifting.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Accounts extends BaseEntity {


    // id (primary key)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;
    // email address
    @Column(name = "email_address")
    private String emailAddress;
    //username
    @Column(name = "username")
    private  String username;
    // account type
    @ManyToOne
    @JoinColumn(name = "account_type_id", nullable = false)
    private AccountsType accountType;
}
