package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVendorConfirmationLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVendorConfirmationLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorConfirmationLineRepository extends JpaRepository<MVendorConfirmationLine, Long>, JpaSpecificationExecutor<MVendorConfirmationLine> {
}
