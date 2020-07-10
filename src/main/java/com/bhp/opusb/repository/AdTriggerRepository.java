package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdTrigger;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdTrigger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdTriggerRepository extends JpaRepository<AdTrigger, Long>, JpaSpecificationExecutor<AdTrigger> {
}
