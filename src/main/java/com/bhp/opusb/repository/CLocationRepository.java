package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CLocation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CLocationRepository extends JpaRepository<CLocation, Long>, JpaSpecificationExecutor<CLocation> {
}
