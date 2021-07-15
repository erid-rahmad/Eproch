package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CPrequalMethodSubCriteria;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CPrequalMethodSubCriteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPrequalMethodSubCriteriaRepository extends JpaRepository<CPrequalMethodSubCriteria, Long>, JpaSpecificationExecutor<CPrequalMethodSubCriteria> {
}
