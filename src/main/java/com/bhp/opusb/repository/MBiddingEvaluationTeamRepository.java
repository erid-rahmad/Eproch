package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingEvaluationTeam;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingEvaluationTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingEvaluationTeamRepository extends JpaRepository<MBiddingEvaluationTeam, Long>, JpaSpecificationExecutor<MBiddingEvaluationTeam> {
    public MBiddingEvaluationTeam findByPrequalification_Id(Long id);
}
