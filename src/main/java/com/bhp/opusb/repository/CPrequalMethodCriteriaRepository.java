package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CPrequalMethodCriteria;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CPrequalMethodCriteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPrequalMethodCriteriaRepository extends JpaRepository<CPrequalMethodCriteria, Long>, JpaSpecificationExecutor<CPrequalMethodCriteria> {
}
