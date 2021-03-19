package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CPaymentSchedule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CPaymentSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPaymentScheduleRepository extends JpaRepository<CPaymentSchedule, Long>, JpaSpecificationExecutor<CPaymentSchedule> {
}
