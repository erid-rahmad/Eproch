package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MProposalTechnicalFile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MProposalTechnicalFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MProposalTechnicalFileRepository extends JpaRepository<MProposalTechnicalFile, Long>, JpaSpecificationExecutor<MProposalTechnicalFile> {
}
