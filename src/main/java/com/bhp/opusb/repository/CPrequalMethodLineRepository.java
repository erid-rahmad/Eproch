package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CPrequalMethodLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CPrequalMethodLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPrequalMethodLineRepository extends JpaRepository<CPrequalMethodLine, Long>, JpaSpecificationExecutor<CPrequalMethodLine> {
}
