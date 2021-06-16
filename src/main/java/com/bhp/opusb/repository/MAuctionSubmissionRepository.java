package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.MAuctionSubmission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MAuctionSubmission entity.
 */
@Repository
public interface MAuctionSubmissionRepository extends JpaRepository<MAuctionSubmission, Long>, JpaSpecificationExecutor<MAuctionSubmission> {

  Optional<MAuctionSubmission> findFirstByAuction_IdAndVendor_Id(Long auctionId, Long vendorId);
}
