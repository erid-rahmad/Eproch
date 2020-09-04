package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CCalendar;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CCalendar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CCalendarRepository extends JpaRepository<CCalendar, Long>, JpaSpecificationExecutor<CCalendar> {
}
