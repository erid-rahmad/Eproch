package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CTaxInvoice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CTaxInvoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CTaxInvoiceRepository extends JpaRepository<CTaxInvoice, Long>, JpaSpecificationExecutor<CTaxInvoice> {
}
