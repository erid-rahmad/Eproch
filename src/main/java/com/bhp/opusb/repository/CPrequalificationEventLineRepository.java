package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CPrequalificationEventLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CPrequalificationEventLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPrequalificationEventLineRepository extends JpaRepository<CPrequalificationEventLine, Long>, JpaSpecificationExecutor<CPrequalificationEventLine> {
}
