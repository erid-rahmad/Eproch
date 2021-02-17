package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingLineRepository extends JpaRepository<MBiddingLine, Long>, JpaSpecificationExecutor<MBiddingLine> {
}
