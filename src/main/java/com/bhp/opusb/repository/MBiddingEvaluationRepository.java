package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingEvaluation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingEvaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingEvaluationRepository extends JpaRepository<MBiddingEvaluation, Long>, JpaSpecificationExecutor<MBiddingEvaluation> {
}
