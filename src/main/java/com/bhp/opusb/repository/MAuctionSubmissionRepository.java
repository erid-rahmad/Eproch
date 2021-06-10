package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MAuctionSubmission;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MAuctionSubmission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MAuctionSubmissionRepository extends JpaRepository<MAuctionSubmission, Long>, JpaSpecificationExecutor<MAuctionSubmission> {
}
