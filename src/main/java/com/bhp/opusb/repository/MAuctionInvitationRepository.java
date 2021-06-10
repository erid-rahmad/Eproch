package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MAuctionInvitation;

import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MAuctionInvitation entity.
 */
@Repository
public interface MAuctionInvitationRepository extends GenericDocumentRepository<MAuctionInvitation, Long> {
}
