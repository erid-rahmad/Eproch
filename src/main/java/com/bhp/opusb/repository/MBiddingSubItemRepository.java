package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingSubItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingSubItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingSubItemRepository extends JpaRepository<MBiddingSubItem, Long>, JpaSpecificationExecutor<MBiddingSubItem> {
}
