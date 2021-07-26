package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MContractTeamLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MContractTeamLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MContractTeamLineRepository extends JpaRepository<MContractTeamLine, Long>, JpaSpecificationExecutor<MContractTeamLine> {
}
