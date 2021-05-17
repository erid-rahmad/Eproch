package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CPrequalificationEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CPrequalificationEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPrequalificationEventRepository extends JpaRepository<CPrequalificationEvent, Long>, JpaSpecificationExecutor<CPrequalificationEvent> {
}
