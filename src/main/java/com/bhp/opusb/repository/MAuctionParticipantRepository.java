package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MAuctionParticipant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MAuctionParticipant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MAuctionParticipantRepository extends JpaRepository<MAuctionParticipant, Long>, JpaSpecificationExecutor<MAuctionParticipant> {
}
