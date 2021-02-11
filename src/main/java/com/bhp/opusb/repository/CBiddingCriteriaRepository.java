package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CBiddingCriteria;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CBiddingCriteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CBiddingCriteriaRepository extends JpaRepository<CBiddingCriteria, Long>, JpaSpecificationExecutor<CBiddingCriteria> {
}
