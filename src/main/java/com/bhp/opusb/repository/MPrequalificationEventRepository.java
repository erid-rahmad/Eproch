package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPrequalificationEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalificationEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalificationEventRepository extends JpaRepository<MPrequalificationEvent, Long>, JpaSpecificationExecutor<MPrequalificationEvent> {
}
