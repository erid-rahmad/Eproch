package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MContractDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MContractDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MContractDocumentRepository extends JpaRepository<MContractDocument, Long>, JpaSpecificationExecutor<MContractDocument> {
}
