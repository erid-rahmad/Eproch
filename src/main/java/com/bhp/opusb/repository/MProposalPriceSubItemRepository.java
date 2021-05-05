package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MProposalPriceSubItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MProposalPriceSubItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MProposalPriceSubItemRepository extends JpaRepository<MProposalPriceSubItem, Long>, JpaSpecificationExecutor<MProposalPriceSubItem> {
}
