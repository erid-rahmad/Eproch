package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MAuctionItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MAuctionItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MAuctionItemRepository extends JpaRepository<MAuctionItem, Long>, JpaSpecificationExecutor<MAuctionItem> {
}
