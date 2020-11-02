package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CProductCategoryAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CProductCategoryAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CProductCategoryAccountRepository extends JpaRepository<CProductCategoryAccount, Long>, JpaSpecificationExecutor<CProductCategoryAccount> {
}
