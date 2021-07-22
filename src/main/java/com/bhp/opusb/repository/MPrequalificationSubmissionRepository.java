package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.MPrequalificationSubmission;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalificationSubmission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalificationSubmissionRepository extends GenericDocumentRepository<MPrequalificationSubmission, Long>, JpaSpecificationExecutor<MPrequalificationSubmission> {
    @Query("select mpre from MPrequalificationSubmission mpre where mpre.vendor.id = ?1 and mpre.prequalification.id = ?2")
    Optional<MPrequalificationSubmission> findExisting(Long vendorId, Long prequalificationId);
}
