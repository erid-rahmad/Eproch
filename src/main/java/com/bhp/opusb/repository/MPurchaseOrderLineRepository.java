package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPurchaseOrderLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPurchaseOrderLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPurchaseOrderLineRepository extends JpaRepository<MPurchaseOrderLine, Long>, JpaSpecificationExecutor<MPurchaseOrderLine> {
}
