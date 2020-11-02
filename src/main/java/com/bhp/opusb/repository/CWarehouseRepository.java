package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CWarehouse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CWarehouse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CWarehouseRepository extends JpaRepository<CWarehouse, Long>, JpaSpecificationExecutor<CWarehouse> {
}
