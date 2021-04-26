package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingInvitation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingInvitation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingInvitationRepository extends JpaRepository<MBiddingInvitation, Long>, JpaSpecificationExecutor<MBiddingInvitation> {
}
