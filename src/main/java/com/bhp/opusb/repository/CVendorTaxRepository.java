package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CVendorTax;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CVendorTax entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CVendorTaxRepository extends JpaRepository<CVendorTax, Long>, JpaSpecificationExecutor<CVendorTax> {
}
