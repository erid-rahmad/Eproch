package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CVendorLocation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CVendorLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CVendorLocationRepository extends JpaRepository<CVendorLocation, Long>, JpaSpecificationExecutor<CVendorLocation> {
}
