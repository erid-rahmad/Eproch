package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBidding;

import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBidding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingRepository extends GenericDocumentRepository<MBidding, Long> {
}
