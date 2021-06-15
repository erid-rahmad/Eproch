package com.bhp.opusb.repository;

import java.util.Collection;

import com.bhp.opusb.domain.MBiddingSchedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingSchedule entity.
 */
@Repository
public interface MBiddingScheduleRepository extends JpaRepository<MBiddingSchedule, Long>, JpaSpecificationExecutor<MBiddingSchedule> {

  @Modifying
  @Query("DELETE FROM MBiddingSchedule s WHERE s.eventTypeLine.id NOT IN (:lineIds) AND s.bidding.id = :biddingId")
  int deleteByEventTypeLineIdNotIn(@Param("lineIds") Collection<Long> eventTypeLineIds, @Param("biddingId") Long biddingId);
}
