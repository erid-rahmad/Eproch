package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CProductClassification;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CProductClassification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CProductClassificationRepository extends JpaRepository<CProductClassification, Long>, JpaSpecificationExecutor<CProductClassification> {
}
