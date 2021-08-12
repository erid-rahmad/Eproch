package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPrequalAnnouncementResult;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalAnnouncementResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalAnnouncementResultRepository extends JpaRepository<MPrequalAnnouncementResult, Long>, JpaSpecificationExecutor<MPrequalAnnouncementResult> {
}
