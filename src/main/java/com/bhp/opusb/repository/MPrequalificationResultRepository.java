package com.bhp.opusb.repository;

import java.util.List;

import com.bhp.opusb.domain.MPrequalificationResult;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalificationResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalificationResultRepository extends JpaRepository<MPrequalificationResult, Long>, JpaSpecificationExecutor<MPrequalificationResult> {
    @Query("select mpr from MPrequalificationResult mpr where mpr.announcementResult.id=?1 and mpr.vendor.id=?2")
    List<MPrequalificationResult> findExistingResult(Long announcementResultId, Long vendorId);
}
