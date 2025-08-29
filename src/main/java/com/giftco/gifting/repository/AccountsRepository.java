package com.giftco.gifting.repository;

import com.giftco.gifting.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// This interface extends JpaRepository, which provides JPA related methods for the entity class
//the Long is the type of the primary key of the Accounts entity
@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    //findByAccountId
    Optional<Accounts> findByAccountId(Long id);
    //findByEmailAddress
    Optional<Accounts> findByEmailAddress(String emailAddress);
    //deleteByAccountId
    @Transactional
    @Modifying
    void deleteByAccountId(Long id);
}
