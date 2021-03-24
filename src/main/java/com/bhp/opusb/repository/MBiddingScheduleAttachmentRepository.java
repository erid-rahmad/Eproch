package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingScheduleAttachment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingScheduleAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingScheduleAttachmentRepository extends JpaRepository<MBiddingScheduleAttachment, Long>, JpaSpecificationExecutor<MBiddingScheduleAttachment> {
}
