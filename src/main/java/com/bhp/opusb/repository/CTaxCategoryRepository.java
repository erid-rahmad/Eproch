package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CTaxCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CTaxCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CTaxCategoryRepository extends JpaRepository<CTaxCategory, Long>, JpaSpecificationExecutor<CTaxCategory> {
}
