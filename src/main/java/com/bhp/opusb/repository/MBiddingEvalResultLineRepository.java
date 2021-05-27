package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingEvalResultLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingEvalResultLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingEvalResultLineRepository extends JpaRepository<MBiddingEvalResultLine, Long>, JpaSpecificationExecutor<MBiddingEvalResultLine> {
}
