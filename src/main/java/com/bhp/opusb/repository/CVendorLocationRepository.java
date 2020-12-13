package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.CVendorLocation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CVendorLocation entity.
 */
@Repository
public interface CVendorLocationRepository extends JpaRepository<CVendorLocation, Long>, JpaSpecificationExecutor<CVendorLocation> {

  Optional<CVendorLocation> findFirstByInvoiceAddressTrueAndVendor_Id(Long vendorId);
}
