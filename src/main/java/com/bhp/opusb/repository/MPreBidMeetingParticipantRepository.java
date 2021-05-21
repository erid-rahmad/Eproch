package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.MPreBidMeetingParticipant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPreBidMeetingParticipant entity.
 */
@Repository
public interface MPreBidMeetingParticipantRepository extends JpaRepository<MPreBidMeetingParticipant, Long>, JpaSpecificationExecutor<MPreBidMeetingParticipant> {

  @Modifying
  @Query("DELETE FROM MPreBidMeetingParticipant WHERE preBidMeeting.id = :preBidMeetingId AND vendor.id IN (:vendorIds)")
  void deleteByVendorIds(@Param("preBidMeetingId") Long preBidMeetingId, @Param("vendorIds") List<Long> vendorIds);
}
