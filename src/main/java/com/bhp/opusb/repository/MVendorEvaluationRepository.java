package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVendorEvaluation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVendorEvaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorEvaluationRepository extends GenericDocumentRepository<MVendorEvaluation, Long> {
}
