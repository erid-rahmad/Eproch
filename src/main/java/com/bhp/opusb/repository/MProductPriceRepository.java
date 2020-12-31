package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MProductPrice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MProductPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MProductPriceRepository extends JpaRepository<MProductPrice, Long>, JpaSpecificationExecutor<MProductPrice> {
}
