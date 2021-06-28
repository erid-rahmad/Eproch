package com.bhp.opusb.repository;


import com.bhp.opusb.domain.MBidNegoPriceLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MBidNegoPriceLine entity.
 */

@Repository
public interface MBidNegoPriceLineRepository extends JpaRepository<MBidNegoPriceLine, Long>, JpaSpecificationExecutor<MBidNegoPriceLine> {
    @Query(value = "SELECT a FROM MBidNegoPriceLine a WHERE a.bidNegoPrice.id=?1" )
    List<MBidNegoPriceLine> findbyHeader(long a);
}
