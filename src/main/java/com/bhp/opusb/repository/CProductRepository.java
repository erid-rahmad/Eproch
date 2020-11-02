package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CProduct;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CProductRepository extends JpaRepository<CProduct, Long>, JpaSpecificationExecutor<CProduct> {
}
