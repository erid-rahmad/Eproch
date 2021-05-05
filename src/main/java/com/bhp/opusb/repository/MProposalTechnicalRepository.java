package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MProposalTechnical;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MProposalTechnical entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MProposalTechnicalRepository extends JpaRepository<MProposalTechnical, Long>, JpaSpecificationExecutor<MProposalTechnical> {
}
