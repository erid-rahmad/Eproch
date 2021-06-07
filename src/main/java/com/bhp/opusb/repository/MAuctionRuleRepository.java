package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MAuctionRule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MAuctionRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MAuctionRuleRepository extends JpaRepository<MAuctionRule, Long>, JpaSpecificationExecutor<MAuctionRule> {
}
