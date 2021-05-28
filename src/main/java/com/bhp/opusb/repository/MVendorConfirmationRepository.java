package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVendorConfirmation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVendorConfirmation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorConfirmationRepository extends JpaRepository<MVendorConfirmation, Long>, JpaSpecificationExecutor<MVendorConfirmation> {
}
