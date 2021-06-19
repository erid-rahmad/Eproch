package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVendorEvaluationLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVendorEvaluationLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorEvaluationLineRepository extends JpaRepository<MVendorEvaluationLine, Long>, JpaSpecificationExecutor<MVendorEvaluationLine> {
}
