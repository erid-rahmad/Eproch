package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CTask;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CTaskRepository extends JpaRepository<CTask, Long>, JpaSpecificationExecutor<CTask> {
}
