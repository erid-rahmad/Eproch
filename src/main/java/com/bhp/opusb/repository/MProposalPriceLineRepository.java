package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.MProposalPrice;
import com.bhp.opusb.domain.MProposalPriceLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MProposalPriceLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MProposalPriceLineRepository extends JpaRepository<MProposalPriceLine, Long>, JpaSpecificationExecutor<MProposalPriceLine> {
    List<MProposalPriceLine> findByProposalPrice(MProposalPrice proposalPrice);
}
