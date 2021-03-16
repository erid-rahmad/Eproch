package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBiddingSubmission;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingSubmission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBiddingSubmissionRepository extends JpaRepository<MBiddingSubmission, Long>, JpaSpecificationExecutor<MBiddingSubmission> {
}
