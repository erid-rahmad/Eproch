package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPurchaseOrder;
import com.bhp.opusb.domain.MVendorConfirmationLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MVendorConfirmationLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorConfirmationLineRepository extends JpaRepository<MVendorConfirmationLine, Long>, JpaSpecificationExecutor<MVendorConfirmationLine> {
    List<MVendorConfirmationLine> findByStatus(String status);
  }
