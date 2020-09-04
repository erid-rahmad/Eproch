package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CCostCenter;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CCostCenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CCostCenterRepository extends JpaRepository<CCostCenter, Long>, JpaSpecificationExecutor<CCostCenter> {
}
