package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CClauseLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CClauseLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CClauseLineRepository extends JpaRepository<CClauseLine, Long>, JpaSpecificationExecutor<CClauseLine> {
}
