package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MRequisitionLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MRequisitionLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MRequisitionLineRepository extends JpaRepository<MRequisitionLine, Long>, JpaSpecificationExecutor<MRequisitionLine> {
}
