package com.crediall.api.repository;

import com.crediall.api.domain.CrediallWallet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CrediallWallet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CrediallWalletRepository extends JpaRepository<CrediallWallet, Long> {}
