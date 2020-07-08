package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CBusinessCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CBusinessCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CBusinessCategoryRepository extends JpaRepository<CBusinessCategory, Long>, JpaSpecificationExecutor<CBusinessCategory> {
}
