package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBidNegoPrice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MBidNegoPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBidNegoPriceRepository extends JpaRepository<MBidNegoPrice, Long>, JpaSpecificationExecutor<MBidNegoPrice> {

    @Query(value = "SELECT a FROM MBidNegoPrice a WHERE a.negotiationLine.id=?1" )
    List<MBidNegoPrice> findbynegoline(long a);


}
