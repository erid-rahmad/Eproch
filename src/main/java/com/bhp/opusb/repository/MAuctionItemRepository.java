package com.bhp.opusb.repository;

import java.util.List;
import java.util.Optional;

import com.bhp.opusb.domain.MAuction;
import com.bhp.opusb.domain.MAuctionItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MAuctionItem entity.
 */
@Repository
public interface MAuctionItemRepository extends JpaRepository<MAuctionItem, Long>, JpaSpecificationExecutor<MAuctionItem> {

  @Modifying
  @Query("UPDATE MAuctionItem SET auctionStatus = :status WHERE auction = :auction")
  int updateStatus(@Param("auction") MAuction auction, @Param("status") String status);

  List<MAuctionItem> findByAuctionAndAuctionStatusNot(MAuction auction, String status);

  /**
   * Find an auction item of an auction, with a specific status.
   * @param auction
   * @param status
   * @return
   */
  Optional<MAuctionItem> findFirstByAuctionAndAuctionStatus(MAuction auction, String status);

  /**
   * Find the first MAuctionItem that's not been started yet.
   * @param auction
   * @return
   */
  Optional<MAuctionItem> findFirstByAuctionAndAuctionStatusNullOrderById(MAuction auction);
}
