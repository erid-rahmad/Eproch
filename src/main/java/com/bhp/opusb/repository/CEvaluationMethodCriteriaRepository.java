package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CEvaluationMethodCriteria;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CEvaluationMethodCriteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CEvaluationMethodCriteriaRepository extends JpaRepository<CEvaluationMethodCriteria, Long>, JpaSpecificationExecutor<CEvaluationMethodCriteria> {
}
