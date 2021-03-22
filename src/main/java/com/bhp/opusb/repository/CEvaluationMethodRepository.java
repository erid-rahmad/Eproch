package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CEvaluationMethod;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CEvaluationMethod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CEvaluationMethodRepository extends JpaRepository<CEvaluationMethod, Long>, JpaSpecificationExecutor<CEvaluationMethod> {
}
