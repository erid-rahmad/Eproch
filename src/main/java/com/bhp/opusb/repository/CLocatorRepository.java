package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CLocator;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CLocator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CLocatorRepository extends JpaRepository<CLocator, Long>, JpaSpecificationExecutor<CLocator> {
}
