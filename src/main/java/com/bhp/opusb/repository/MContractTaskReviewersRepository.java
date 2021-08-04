package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MContractTaskReviewers;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MContractTaskReviewers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MContractTaskReviewersRepository extends JpaRepository<MContractTaskReviewers, Long>, JpaSpecificationExecutor<MContractTaskReviewers> {
}
