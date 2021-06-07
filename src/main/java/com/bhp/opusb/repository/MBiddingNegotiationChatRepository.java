package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingNegotiationChat;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingNegotiationChat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingNegotiationChatRepository extends JpaRepository<MBiddingNegotiationChat, Long>, JpaSpecificationExecutor<MBiddingNegotiationChat> {
}
