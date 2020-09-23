package com.bhp.opusb.repository;

import com.bhp.opusb.domain.COrganization;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the COrganization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface COrganizationRepository extends JpaRepository<COrganization, Long>, JpaSpecificationExecutor<COrganization> {
}
