package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MProductCatalog;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MProductCatalog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MProductCatalogRepository extends JpaRepository<MProductCatalog, Long>, JpaSpecificationExecutor<MProductCatalog> {
}
