package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MAuctionContent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MAuctionContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MAuctionContentRepository extends JpaRepository<MAuctionContent, Long>, JpaSpecificationExecutor<MAuctionContent> {
}
