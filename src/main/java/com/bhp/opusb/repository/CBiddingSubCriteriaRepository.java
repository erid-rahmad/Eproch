package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CBiddingSubCriteria;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CBiddingSubCriteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CBiddingSubCriteriaRepository extends JpaRepository<CBiddingSubCriteria, Long>, JpaSpecificationExecutor<CBiddingSubCriteria> {
}
