package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPrequalificationResult;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalificationResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalificationResultRepository extends JpaRepository<MPrequalificationResult, Long>, JpaSpecificationExecutor<MPrequalificationResult> {
}
