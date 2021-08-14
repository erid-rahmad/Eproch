package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.MQuoteSupplier;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MQuoteSupplier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MQuoteSupplierRepository extends JpaRepository<MQuoteSupplier, Long>, JpaSpecificationExecutor<MQuoteSupplier> {
    Optional<MQuoteSupplier> findByQuotation_idAndVendor_id(Long quotationId, Long vendorId);
}
