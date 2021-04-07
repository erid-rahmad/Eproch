package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.CEventTypeline;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CEventTypeline entity.
 */
@Repository
public interface CEventTypelineRepository extends JpaRepository<CEventTypeline, Long>, JpaSpecificationExecutor<CEventTypeline> {

  List<CEventTypeline> findByEventType_IdOrderBySequence(Long eventTypeId);
}
