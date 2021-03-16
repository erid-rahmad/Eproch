package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CVendorTax;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CVendorTax entity.
 */
@Repository
public interface CVendorTaxRepository extends JpaRepository<CVendorTax, Long>, JpaSpecificationExecutor<CVendorTax> {

  Optional<CVendorTax> findFirstByVendor(CVendor vendor);
}
