package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CRegion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CRegion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CRegionRepository extends JpaRepository<CRegion, Long>, JpaSpecificationExecutor<CRegion> {
}
