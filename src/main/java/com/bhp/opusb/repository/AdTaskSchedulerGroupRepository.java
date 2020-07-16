package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdTaskSchedulerGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdTaskSchedulerGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdTaskSchedulerGroupRepository extends JpaRepository<AdTaskSchedulerGroup, Long>, JpaSpecificationExecutor<AdTaskSchedulerGroup> {
}
