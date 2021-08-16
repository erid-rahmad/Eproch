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
    @Query("DELETE FROM MPrequalificationSchedule s WHERE s.eventLine.id NOT IN (?1) AND s.prequalification.id = ?2")
    void deleteByEventTypeLineIdNotIn(List<Long> lineIds, Long id);

    @Query("select mps from MPrequalificationSchedule mps where mps.prequalification.id=?1")
    List<MPrequalificationSchedule> findByHeaderId(Long prequalificationId);
}
