package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.MRfqSubmission;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MRfqSubmission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MRfqSubmissionRepository extends JpaRepository<MRfqSubmission, Long>, JpaSpecificationExecutor<MRfqSubmission> {

    Optional<MRfqSubmission> findByQuoteSupplier_Id(Long quoteSupplierId);
}
