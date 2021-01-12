package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPurchaseOrder;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPurchaseOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPurchaseOrderRepository extends JpaRepository<MPurchaseOrder, Long>, JpaSpecificationExecutor<MPurchaseOrder> {
}
