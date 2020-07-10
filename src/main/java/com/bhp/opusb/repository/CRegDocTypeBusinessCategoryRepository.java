package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CRegDocTypeBusinessCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CRegDocTypeBusinessCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CRegDocTypeBusinessCategoryRepository extends JpaRepository<CRegDocTypeBusinessCategory, Long>, JpaSpecificationExecutor<CRegDocTypeBusinessCategory> {
}
