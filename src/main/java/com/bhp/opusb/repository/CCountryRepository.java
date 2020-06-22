package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CCountry;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CCountry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CCountryRepository extends JpaRepository<CCountry, Long>, JpaSpecificationExecutor<CCountry> {
}
