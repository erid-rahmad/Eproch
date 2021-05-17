package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBidding;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBidding entity.
 */
@Repository
public interface MBiddingRepository extends GenericDocumentRepository<MBidding, Long> {

  @Query("SELECT b FROM MBiddingSchedule s INNER JOIN s.bidding AS b WHERE s.id = ?1")
  MBidding findFirstByBiddingScheduleId(Long biddingScheduleId);
}
