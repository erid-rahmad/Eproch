package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdTaskApplication;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdTaskApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdTaskApplicationRepository extends JpaRepository<AdTaskApplication, Long>, JpaSpecificationExecutor<AdTaskApplication> {
}
