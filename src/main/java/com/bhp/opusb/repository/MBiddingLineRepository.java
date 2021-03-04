package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MBiddingLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingLineRepository extends JpaRepository<MBiddingLine, Long>, JpaSpecificationExecutor<MBiddingLine> {

    @Query(value = "SELECT a FROM MBiddingLine a WHERE a.bidding.id=?1" )
    List<MBiddingLine> findbyheader(long a);

}
