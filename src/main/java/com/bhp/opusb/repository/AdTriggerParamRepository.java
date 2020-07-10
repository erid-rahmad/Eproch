package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdTriggerParam;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdTriggerParam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdTriggerParamRepository extends JpaRepository<AdTriggerParam, Long>, JpaSpecificationExecutor<AdTriggerParam> {
}
