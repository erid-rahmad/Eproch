package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CClause;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CClause entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CClauseRepository extends JpaRepository<CClause, Long>, JpaSpecificationExecutor<CClause> {
}
