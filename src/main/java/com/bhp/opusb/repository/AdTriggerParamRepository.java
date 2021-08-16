package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.AdTriggerParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdTriggerParam entity.
 */
@Repository
public interface AdTriggerParamRepository extends JpaRepository<AdTriggerParam, Long>, JpaSpecificationExecutor<AdTriggerParam> {

  List<AdTriggerParam> findByAdTrigger_IdAndActiveTrue(Long adTriggerId);
}
