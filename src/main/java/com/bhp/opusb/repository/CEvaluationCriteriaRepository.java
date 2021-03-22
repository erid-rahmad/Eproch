package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CEvaluationCriteria;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CEvaluationCriteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CEvaluationCriteriaRepository extends JpaRepository<CEvaluationCriteria, Long>, JpaSpecificationExecutor<CEvaluationCriteria> {
}
