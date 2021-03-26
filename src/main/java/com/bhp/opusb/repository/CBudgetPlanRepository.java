package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CBudgetPlan;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CBudgetPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CBudgetPlanRepository extends JpaRepository<CBudgetPlan, Long>, JpaSpecificationExecutor<CBudgetPlan> {
}
