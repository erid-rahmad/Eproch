package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CElementValue;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CElementValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CElementValueRepository extends JpaRepository<CElementValue, Long>, JpaSpecificationExecutor<CElementValue> {
}
