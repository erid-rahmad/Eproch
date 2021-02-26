package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPurchaseOrderLine;

import com.bhp.opusb.domain.MRequisitionLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MPurchaseOrderLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPurchaseOrderLineRepository extends JpaRepository<MPurchaseOrderLine, Long>, JpaSpecificationExecutor<MPurchaseOrderLine> {
    @Query(value = "SELECT a FROM MPurchaseOrderLine a WHERE a.purchaseOrder.id=?1" )
    List<MPurchaseOrderLine> mPOlinebyidpr(long a);
}
