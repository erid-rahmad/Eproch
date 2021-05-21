package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingResult;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingResultRepository extends JpaRepository<MBiddingResult, Long>, JpaSpecificationExecutor<MBiddingResult> {
}
