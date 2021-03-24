package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingSubItemLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MBiddingSubItemLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingSubItemLineRepository extends JpaRepository<MBiddingSubItemLine, Long>, JpaSpecificationExecutor<MBiddingSubItemLine> {

    @Query(value = "SELECT a FROM MBiddingSubItemLine a WHERE a.biddingSubItem.id=?1" )
    List<MBiddingSubItemLine> findbyheaderid(long a);

}
