package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingSchedule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingScheduleRepository extends JpaRepository<MBiddingSchedule, Long>, JpaSpecificationExecutor<MBiddingSchedule> {
}
