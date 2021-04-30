package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPreBidMeeting;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPreBidMeeting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPreBidMeetingRepository extends JpaRepository<MPreBidMeeting, Long>, JpaSpecificationExecutor<MPreBidMeeting> {
}
