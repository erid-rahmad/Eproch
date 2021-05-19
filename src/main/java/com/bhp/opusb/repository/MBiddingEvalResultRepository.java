package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingEvalResult;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingEvalResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingEvalResultRepository extends JpaRepository<MBiddingEvalResult, Long>, JpaSpecificationExecutor<MBiddingEvalResult> {
}
