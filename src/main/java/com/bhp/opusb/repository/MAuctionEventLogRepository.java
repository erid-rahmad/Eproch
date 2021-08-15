package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MAuctionEventLog;
import com.bhp.opusb.domain.view.AmountView;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MAuctionEventLog entity.
 */
@Repository
public interface MAuctionEventLogRepository extends JpaRepository<MAuctionEventLog, Long>, JpaSpecificationExecutor<MAuctionEventLog> {

  @Query("SELECT MIN(l.price) AS minPrice FROM MAuctionEventLog l WHERE l.action = 'BID' AND l.auctionItem.id = ?1")
  AmountView getMinPriceByAuctionItemId(Long itemId);
}
