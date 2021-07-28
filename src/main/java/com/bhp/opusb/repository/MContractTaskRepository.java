package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MContractTask;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MContractTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MContractTaskRepository extends JpaRepository<MContractTask, Long>, JpaSpecificationExecutor<MContractTask> {
}
