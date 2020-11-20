package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.CWarehouse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CWarehouse entity.
 */
@Repository
public interface CWarehouseRepository extends JpaRepository<CWarehouse, Long>, JpaSpecificationExecutor<CWarehouse> {

  Optional<CWarehouse> findFirstByCodeAndAdOrganizationId(String code, Long orgId);
}
