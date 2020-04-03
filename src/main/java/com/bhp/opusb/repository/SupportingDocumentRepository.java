package com.bhp.opusb.repository;

import com.bhp.opusb.domain.SupportingDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SupportingDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupportingDocumentRepository extends JpaRepository<SupportingDocument, Long>, JpaSpecificationExecutor<SupportingDocument> {
}
