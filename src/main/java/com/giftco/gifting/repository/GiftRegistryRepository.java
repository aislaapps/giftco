package com.giftco.gifting.repository;

import com.giftco.gifting.entity.GiftRegistry;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiftRegistryRepository extends JpaRepository<GiftRegistry, Long> {
    // get GiftRegistry by name
    Optional<GiftRegistry> findByName(String name);

    //if error occurs during deletion, use @Transactional and @Modifying to handle
    @Transactional
    @Modifying
    void deleteById(Long id);
}
