package com.bhp.opusb.repository;

import java.util.List;
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
   
    @Query(value = "SELECT a FROM MRfqSubmissionLine a WHERE a.submission.id=?1" )
    List<MRfqSubmissionLine> findbyHeader(Long id);
}
