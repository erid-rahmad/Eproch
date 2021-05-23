package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MBiddingSubmission;

import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBiddingSubmission entity.
 */
@Repository
public interface MBiddingSubmissionRepository extends GenericDocumentRepository<MBiddingSubmission, Long> {

  Optional<MBiddingSubmission> findFirstByBiddingAndVendor(MBidding mBidding, CVendor vendor);
}
