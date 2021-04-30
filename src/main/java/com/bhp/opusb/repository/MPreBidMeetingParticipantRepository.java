package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPreBidMeetingParticipant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPreBidMeetingParticipant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPreBidMeetingParticipantRepository extends JpaRepository<MPreBidMeetingParticipant, Long>, JpaSpecificationExecutor<MPreBidMeetingParticipant> {
}
