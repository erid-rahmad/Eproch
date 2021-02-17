package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVendorScoring;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVendorScoring entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorScoringRepository extends JpaRepository<MVendorScoring, Long>, JpaSpecificationExecutor<MVendorScoring> {
}
