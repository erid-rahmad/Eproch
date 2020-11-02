package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CProductGroupAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CProductGroupAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CProductGroupAccountRepository extends JpaRepository<CProductGroupAccount, Long>, JpaSpecificationExecutor<CProductGroupAccount> {
}
