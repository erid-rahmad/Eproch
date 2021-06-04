package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingNegotiationLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingNegotiationLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingNegotiationLineRepository extends JpaRepository<MBiddingNegotiationLine, Long>, JpaSpecificationExecutor<MBiddingNegotiationLine> {
}
