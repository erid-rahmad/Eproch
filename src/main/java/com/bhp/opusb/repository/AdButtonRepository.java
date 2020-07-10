package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdButton;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdButton entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdButtonRepository extends JpaRepository<AdButton, Long>, JpaSpecificationExecutor<AdButton> {
}
