package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CPrequalificationStep;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CPrequalificationStep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPrequalificationStepRepository extends JpaRepository<CPrequalificationStep, Long>, JpaSpecificationExecutor<CPrequalificationStep> {
}
