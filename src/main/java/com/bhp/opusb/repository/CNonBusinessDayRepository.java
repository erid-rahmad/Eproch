package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CNonBusinessDay;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CNonBusinessDay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CNonBusinessDayRepository extends JpaRepository<CNonBusinessDay, Long>, JpaSpecificationExecutor<CNonBusinessDay> {
}
