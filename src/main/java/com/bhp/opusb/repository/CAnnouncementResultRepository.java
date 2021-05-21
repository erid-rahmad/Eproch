package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CAnnouncementResult;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CAnnouncementResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CAnnouncementResultRepository extends JpaRepository<CAnnouncementResult, Long>, JpaSpecificationExecutor<CAnnouncementResult> {
}
