package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CAnnouncement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CAnnouncement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CAnnouncementRepository extends JpaRepository<CAnnouncement, Long>, JpaSpecificationExecutor<CAnnouncement> {
}
