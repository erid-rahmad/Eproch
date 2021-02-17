package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVendorInvitation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVendorInvitation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorInvitationRepository extends JpaRepository<MVendorInvitation, Long>, JpaSpecificationExecutor<MVendorInvitation> {
}
