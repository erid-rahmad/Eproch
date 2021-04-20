package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CEvalMethodCriteriaLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CEvalMethodCriteriaLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CEvalMethodCriteriaLineRepository extends JpaRepository<CEvalMethodCriteriaLine, Long>, JpaSpecificationExecutor<CEvalMethodCriteriaLine> {
}
