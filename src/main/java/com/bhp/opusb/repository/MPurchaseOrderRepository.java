package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPurchaseOrder;

import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPurchaseOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPurchaseOrderRepository extends GenericDocumentRepository<MPurchaseOrder, Long> {
}
