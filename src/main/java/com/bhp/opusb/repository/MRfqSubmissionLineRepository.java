package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.MRfqSubmissionLine;
import com.bhp.opusb.service.dto.MRfqSubmissionLineDTO;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MRfqSubmissionLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MRfqSubmissionLineRepository extends JpaRepository<MRfqSubmissionLine, Long>, JpaSpecificationExecutor<MRfqSubmissionLine> {

    Optional<MRfqSubmissionLine> findFirstBySubmission_IdAndQuotationLine_Id(Long submissionId, Long quotationLineId);
}
