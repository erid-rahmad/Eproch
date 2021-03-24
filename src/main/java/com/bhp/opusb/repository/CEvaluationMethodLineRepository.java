package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CEvaluationMethodLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CEvaluationMethodLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CEvaluationMethodLineRepository extends JpaRepository<CEvaluationMethodLine, Long>, JpaSpecificationExecutor<CEvaluationMethodLine> {
}
