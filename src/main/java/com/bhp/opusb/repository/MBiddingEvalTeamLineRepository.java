package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingEvalTeamLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingEvalTeamLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingEvalTeamLineRepository extends JpaRepository<MBiddingEvalTeamLine, Long>, JpaSpecificationExecutor<MBiddingEvalTeamLine> {
}
