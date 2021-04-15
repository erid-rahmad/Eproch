package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CBiddingSubCriteriaLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CBiddingSubCriteriaLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CBiddingSubCriteriaLineRepository extends JpaRepository<CBiddingSubCriteriaLine, Long>, JpaSpecificationExecutor<CBiddingSubCriteriaLine> {
}
