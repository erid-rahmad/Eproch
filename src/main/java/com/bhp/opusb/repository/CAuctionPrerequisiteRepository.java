package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CAuctionPrerequisite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CAuctionPrerequisite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CAuctionPrerequisiteRepository extends JpaRepository<CAuctionPrerequisite, Long>, JpaSpecificationExecutor<CAuctionPrerequisite> {
}
