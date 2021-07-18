package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.MPrequalificationSchedule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalificationSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalificationScheduleRepository extends JpaRepository<MPrequalificationSchedule, Long>, JpaSpecificationExecutor<MPrequalificationSchedule> {

    @Modifying
    @Query("DELETE FROM MPrequalificationSchedule s WHERE s.eventLine.id NOT IN (:lineIds) AND s.prequalification.id = :id")
    void deleteByEventTypeLineIdNotIn(List<Long> lineIds, Long id);
}
