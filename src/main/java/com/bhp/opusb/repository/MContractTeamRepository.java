package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MContractTeam;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MContractTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MContractTeamRepository extends JpaRepository<MContractTeam, Long>, JpaSpecificationExecutor<MContractTeam> {
    public MContractTeam findByContract_Id(Long id);
}
