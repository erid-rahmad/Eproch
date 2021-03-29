package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CVendorEvaluationLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CVendorEvaluationLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CVendorEvaluationLineRepository extends JpaRepository<CVendorEvaluationLine, Long>, JpaSpecificationExecutor<CVendorEvaluationLine> {
}
