package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdWatchList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdWatchList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdWatchListRepository extends JpaRepository<AdWatchList, Long>, JpaSpecificationExecutor<AdWatchList> {
}
