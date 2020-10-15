package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ADOrganizationInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADOrganizationInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ADOrganizationInfoRepository extends JpaRepository<ADOrganizationInfo, Long>, JpaSpecificationExecutor<ADOrganizationInfo> {
}
