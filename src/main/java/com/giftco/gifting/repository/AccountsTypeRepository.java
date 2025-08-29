package com.giftco.gifting.repository;

import com.giftco.gifting.entity.AccountsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsTypeRepository extends JpaRepository<AccountsType, Long> {
    Optional<AccountsType> findByAccountTypeName(String typeName);
}
