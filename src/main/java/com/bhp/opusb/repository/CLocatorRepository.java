package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.CLocator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CLocator entity.
 */
@Repository
public interface CLocatorRepository extends JpaRepository<CLocator, Long>, JpaSpecificationExecutor<CLocator> {

  Optional<CLocator> findFirstByCodeAndWarehouseIdAndAdOrganizationId(String code, Long warehouseId, Long orgId);
}
