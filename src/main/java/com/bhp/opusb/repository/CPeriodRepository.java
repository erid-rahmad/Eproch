package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CPeriod;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CPeriod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPeriodRepository extends JpaRepository<CPeriod, Long>, JpaSpecificationExecutor<CPeriod> {
}
