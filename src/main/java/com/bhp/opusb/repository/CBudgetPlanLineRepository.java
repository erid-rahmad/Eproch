package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CBudgetPlanLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CBudgetPlanLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CBudgetPlanLineRepository extends JpaRepository<CBudgetPlanLine, Long>, JpaSpecificationExecutor<CBudgetPlanLine> {
}
