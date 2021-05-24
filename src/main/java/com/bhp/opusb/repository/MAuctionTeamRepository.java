package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MAuctionTeam;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MAuctionTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MAuctionTeamRepository extends JpaRepository<MAuctionTeam, Long>, JpaSpecificationExecutor<MAuctionTeam> {
}
