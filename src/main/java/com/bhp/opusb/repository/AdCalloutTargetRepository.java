package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdCalloutTarget;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdCalloutTarget entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdCalloutTargetRepository extends JpaRepository<AdCalloutTarget, Long>, JpaSpecificationExecutor<AdCalloutTarget> {
}
