package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdWatchListItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdWatchListItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdWatchListItemRepository extends JpaRepository<AdWatchListItem, Long>, JpaSpecificationExecutor<AdWatchListItem> {
}
