package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdCallout;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdCallout entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdCalloutRepository extends JpaRepository<AdCallout, Long>, JpaSpecificationExecutor<AdCallout> {
}
