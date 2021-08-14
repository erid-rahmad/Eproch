package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.MRequisition;

import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MRequisition entity.
 */
@Repository
public interface MRequisitionRepository extends GenericDocumentRepository<MRequisition, Long> {
    List<MRequisition> findByDocumentNo(String documentNo);
}
