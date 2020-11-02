package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CRegistrationDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CRegistrationDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CRegistrationDocumentRepository extends JpaRepository<CRegistrationDocument, Long>, JpaSpecificationExecutor<CRegistrationDocument> {
}
