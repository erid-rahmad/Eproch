package com.bhp.opusb.repository;

import com.bhp.opusb.domain.BusinessCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BusinessCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessCategoryRepository extends JpaRepository<BusinessCategory, Long>, JpaSpecificationExecutor<BusinessCategory> {
}
