package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MDocumentSchedule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MDocumentSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MDocumentScheduleRepository extends JpaRepository<MDocumentSchedule, Long>, JpaSpecificationExecutor<MDocumentSchedule> {
}
