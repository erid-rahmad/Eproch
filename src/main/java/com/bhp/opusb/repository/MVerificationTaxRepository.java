package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVerificationTax;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVerificationTax entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVerificationTaxRepository extends JpaRepository<MVerificationTax, Long>, JpaSpecificationExecutor<MVerificationTax> {
}
