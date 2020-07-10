package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdTaskScheduler;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdTaskScheduler entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdTaskSchedulerRepository extends JpaRepository<AdTaskScheduler, Long>, JpaSpecificationExecutor<AdTaskScheduler> {
}
