package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBidNegoPriceLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBidNegoPriceLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBidNegoPriceLineRepository extends JpaRepository<MBidNegoPriceLine, Long>, JpaSpecificationExecutor<MBidNegoPriceLine> {
}
