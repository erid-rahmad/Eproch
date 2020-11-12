package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CVendorGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CVendorGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CVendorGroupRepository extends JpaRepository<CVendorGroup, Long>, JpaSpecificationExecutor<CVendorGroup> {
}
