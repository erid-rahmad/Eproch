package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MContractClauseDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MContractClauseDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MContractClauseDocumentRepository extends JpaRepository<MContractClauseDocument, Long>, JpaSpecificationExecutor<MContractClauseDocument> {
}
