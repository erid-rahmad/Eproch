package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdValidationRule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdValidationRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdValidationRuleRepository extends JpaRepository<AdValidationRule, Long>, JpaSpecificationExecutor<AdValidationRule> {
}
