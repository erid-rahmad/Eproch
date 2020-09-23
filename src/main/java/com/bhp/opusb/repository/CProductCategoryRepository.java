package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CProductCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CProductCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CProductCategoryRepository extends JpaRepository<CProductCategory, Long>, JpaSpecificationExecutor<CProductCategory> {
}
