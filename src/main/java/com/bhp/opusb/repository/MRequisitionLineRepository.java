package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MRequisitionLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MRequisitionLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MRequisitionLineRepository extends JpaRepository<MRequisitionLine, Long>, JpaSpecificationExecutor<MRequisitionLine> {

    @Query(value = "SELECT a FROM MRequisitionLine a WHERE a.requisition.id=?1" )
    List<MRequisitionLine> mReqlinebyidpr(long a);
}
