package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CVendorEvaluation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CVendorEvaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CVendorEvaluationRepository extends JpaRepository<CVendorEvaluation, Long>, JpaSpecificationExecutor<CVendorEvaluation> {
}
