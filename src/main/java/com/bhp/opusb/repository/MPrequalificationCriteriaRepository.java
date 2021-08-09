package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPrequalificationCriteria;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalificationCriteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalificationCriteriaRepository extends JpaRepository<MPrequalificationCriteria, Long>, JpaSpecificationExecutor<MPrequalificationCriteria> {
}
