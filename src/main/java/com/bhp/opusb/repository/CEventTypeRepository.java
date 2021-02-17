package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CEventType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CEventType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CEventTypeRepository extends JpaRepository<CEventType, Long>, JpaSpecificationExecutor<CEventType> {
}
