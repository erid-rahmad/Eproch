package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CVendor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CVendor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CVendorRepository extends JpaRepository<CVendor, Long>, JpaSpecificationExecutor<CVendor> {
}
