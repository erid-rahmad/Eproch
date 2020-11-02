package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CVendorBankAcct;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CVendorBankAcct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CVendorBankAcctRepository extends JpaRepository<CVendorBankAcct, Long>, JpaSpecificationExecutor<CVendorBankAcct> {
}
