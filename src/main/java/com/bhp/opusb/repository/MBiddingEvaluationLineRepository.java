package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingEvaluationLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingEvaluationLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingEvaluationLineRepository extends JpaRepository<MBiddingEvaluationLine, Long>, JpaSpecificationExecutor<MBiddingEvaluationLine> {
}
