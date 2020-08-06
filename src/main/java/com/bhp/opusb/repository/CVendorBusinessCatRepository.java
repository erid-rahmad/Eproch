package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CVendorBusinessCat;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CVendorBusinessCat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CVendorBusinessCatRepository extends JpaRepository<CVendorBusinessCat, Long>, JpaSpecificationExecutor<CVendorBusinessCat> {
}
