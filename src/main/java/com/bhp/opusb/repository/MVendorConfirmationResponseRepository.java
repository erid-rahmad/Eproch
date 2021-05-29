package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVendorConfirmationResponse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVendorConfirmationResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorConfirmationResponseRepository extends JpaRepository<MVendorConfirmationResponse, Long>, JpaSpecificationExecutor<MVendorConfirmationResponse> {
}
