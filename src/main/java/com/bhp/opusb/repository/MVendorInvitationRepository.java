package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MVendorInvitation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVendorInvitation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorInvitationRepository extends JpaRepository<MVendorInvitation, Long>, JpaSpecificationExecutor<MVendorInvitation> {
    List<MVendorInvitation> findByAdOrganizationAndBidding(ADOrganization adOrganization, MBidding bidding);
}
