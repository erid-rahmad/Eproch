package com.bhp.opusb.repository;

import java.util.Collection;
import java.util.List;

import com.bhp.opusb.domain.MBiddingSchedule;

import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.domain.MProposalPrice;
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
  void deleteByEventTypeLineIdNotIn(@Param("lineIds") Collection<Long> eventTypeLineIds, @Param("biddingId") Long biddingId);

  @Query(value = "SELECT a FROM MBiddingSchedule a WHERE a.bidding.id=?1" )
  List<MBiddingSchedule> findByBiddingId (long a);

}
