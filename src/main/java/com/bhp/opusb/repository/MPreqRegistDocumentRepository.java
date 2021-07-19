package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPreqRegistDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPreqRegistDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPreqRegistDocumentRepository extends JpaRepository<MPreqRegistDocument, Long>, JpaSpecificationExecutor<MPreqRegistDocument> {
}
