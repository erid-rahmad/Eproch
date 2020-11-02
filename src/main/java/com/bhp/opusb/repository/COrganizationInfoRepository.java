package com.bhp.opusb.repository;

import com.bhp.opusb.domain.COrganizationInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the COrganizationInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface COrganizationInfoRepository extends JpaRepository<COrganizationInfo, Long>, JpaSpecificationExecutor<COrganizationInfo> {
}
