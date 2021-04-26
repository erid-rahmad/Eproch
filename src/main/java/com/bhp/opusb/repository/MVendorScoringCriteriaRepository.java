package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVendorScoringCriteria;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVendorScoringCriteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorScoringCriteriaRepository extends JpaRepository<MVendorScoringCriteria, Long>, JpaSpecificationExecutor<MVendorScoringCriteria> {
}
