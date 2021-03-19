package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.MBiddingLine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingLine entity.
 */
@Repository
public interface MBiddingLineRepository extends JpaRepository<MBiddingLine, Long>, JpaSpecificationExecutor<MBiddingLine> {

    @Query(value = "SELECT a FROM MBiddingLine a WHERE a.bidding.id=?1" )
    List<MBiddingLine> findbyheader(long a);

}
