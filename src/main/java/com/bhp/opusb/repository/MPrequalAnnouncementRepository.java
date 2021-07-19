package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPrequalAnnouncement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalAnnouncement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalAnnouncementRepository extends JpaRepository<MPrequalAnnouncement, Long>, JpaSpecificationExecutor<MPrequalAnnouncement> {
}
