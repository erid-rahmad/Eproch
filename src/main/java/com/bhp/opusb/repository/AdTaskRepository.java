package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdTask;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdTaskRepository extends JpaRepository<AdTask, Long>, JpaSpecificationExecutor<AdTask> {
}
