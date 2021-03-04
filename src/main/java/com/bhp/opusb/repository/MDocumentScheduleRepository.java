package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MDocumentSchedule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MDocumentSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MDocumentScheduleRepository extends JpaRepository<MDocumentSchedule, Long>, JpaSpecificationExecutor<MDocumentSchedule> {

    @Query(value = "SELECT a FROM MDocumentSchedule a WHERE a.vendorSubmission.id=?1" )
    List<MDocumentSchedule> findbyidbidbidskejul(long a);

}
