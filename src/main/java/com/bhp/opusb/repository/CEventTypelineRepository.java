package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.CEventTypeline;
import com.bhp.opusb.domain.view.MinMaxView;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CEventTypeline entity.
 */
@Repository
public interface CEventTypelineRepository extends JpaRepository<CEventTypeline, Long>, JpaSpecificationExecutor<CEventTypeline> {

  List<CEventTypeline> findByEventType_IdOrderBySequence(Long eventTypeId);

  @Query("SELECT MAX(l.sequence) AS maxSequence, MIN(l.sequence) AS minSequence FROM CEventTypeline l INNER JOIN l.eventType AS e WHERE e.id = ?1")
  MinMaxView findMinMaxSequence(Long eventTypeId);
}
