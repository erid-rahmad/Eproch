package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPreBidMeetingAttachment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPreBidMeetingAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPreBidMeetingAttachmentRepository extends JpaRepository<MPreBidMeetingAttachment, Long>, JpaSpecificationExecutor<MPreBidMeetingAttachment> {
}
