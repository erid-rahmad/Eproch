package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.MVendorConfirmation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVendorConfirmation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorConfirmationRepository extends JpaRepository<MVendorConfirmation, Long>, JpaSpecificationExecutor<MVendorConfirmation> {
    @Query(value="select mvc.id from m_vendor_confirmation mvc where mvc.bidding_id=?1", nativeQuery = true)
    List<Long> findIdsByBiddingId(Long biddingId);
}
