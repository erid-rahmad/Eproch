package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CEventTypeline;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CEventTypeline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CEventTypelineRepository extends JpaRepository<CEventTypeline, Long>, JpaSpecificationExecutor<CEventTypeline> {
}
