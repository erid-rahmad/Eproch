package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVendorScoringLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVendorScoringLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorScoringLineRepository extends JpaRepository<MVendorScoringLine, Long>, JpaSpecificationExecutor<MVendorScoringLine> {
}
