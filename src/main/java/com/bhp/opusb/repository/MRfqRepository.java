package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MRfq;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MRfq entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MRfqRepository extends GenericDocumentRepository<MRfq, Long>, JpaSpecificationExecutor<MRfq> {
}
