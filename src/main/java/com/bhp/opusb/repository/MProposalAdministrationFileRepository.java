package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MProposalAdministrationFile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MProposalAdministrationFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MProposalAdministrationFileRepository extends JpaRepository<MProposalAdministrationFile, Long>, JpaSpecificationExecutor<MProposalAdministrationFile> {
}
