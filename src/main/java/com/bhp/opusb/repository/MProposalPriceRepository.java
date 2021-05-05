package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MProposalPrice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MProposalPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MProposalPriceRepository extends JpaRepository<MProposalPrice, Long>, JpaSpecificationExecutor<MProposalPrice> {
}
