package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingSubItemLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingSubItemLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingSubItemLineRepository extends JpaRepository<MBiddingSubItemLine, Long>, JpaSpecificationExecutor<MBiddingSubItemLine> {
}
