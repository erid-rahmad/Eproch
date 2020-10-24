package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CDocumentType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CDocumentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CDocumentTypeRepository extends JpaRepository<CDocumentType, Long>, JpaSpecificationExecutor<CDocumentType> {
}
