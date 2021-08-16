package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MAuction;

import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MAuction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MAuctionRepository extends GenericDocumentRepository<MAuction, Long> {
}
