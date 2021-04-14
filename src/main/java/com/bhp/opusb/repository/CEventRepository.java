package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CEventRepository extends JpaRepository<CEvent, Long>, JpaSpecificationExecutor<CEvent> {
}
