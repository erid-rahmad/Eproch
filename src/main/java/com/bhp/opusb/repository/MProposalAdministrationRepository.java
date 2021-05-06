package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MProposalAdministration;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MProposalAdministration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MProposalAdministrationRepository extends JpaRepository<MProposalAdministration, Long>, JpaSpecificationExecutor<MProposalAdministration> {
}
