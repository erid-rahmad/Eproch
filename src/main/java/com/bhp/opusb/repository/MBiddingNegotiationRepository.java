package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingNegotiation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingNegotiation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingNegotiationRepository extends JpaRepository<MBiddingNegotiation, Long>, JpaSpecificationExecutor<MBiddingNegotiation> {
}
