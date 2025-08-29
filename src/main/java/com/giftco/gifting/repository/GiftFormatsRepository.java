package com.giftco.gifting.repository;

import com.giftco.gifting.entity.GiftFormats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiftFormatsRepository extends JpaRepository<GiftFormats, Long> {
    Optional<GiftFormats> findByGiftFormatName(String formatName);
    Optional<GiftFormats> findByGiftFormatId(Long id);
}
