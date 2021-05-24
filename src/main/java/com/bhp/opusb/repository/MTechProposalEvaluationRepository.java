package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MTechProposalEvaluation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MTechProposalEvaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MTechProposalEvaluationRepository extends JpaRepository<MTechProposalEvaluation, Long>, JpaSpecificationExecutor<MTechProposalEvaluation> {
}
