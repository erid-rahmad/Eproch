package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CEvalMethodSubCriteria;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CEvalMethodSubCriteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CEvalMethodSubCriteriaRepository extends JpaRepository<CEvalMethodSubCriteria, Long>, JpaSpecificationExecutor<CEvalMethodSubCriteria> {
}
