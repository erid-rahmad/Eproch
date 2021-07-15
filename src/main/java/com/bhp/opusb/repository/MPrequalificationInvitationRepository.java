package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPrequalificationInvitation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalificationInvitation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalificationInvitationRepository extends JpaRepository<MPrequalificationInvitation, Long>, JpaSpecificationExecutor<MPrequalificationInvitation> {
}
