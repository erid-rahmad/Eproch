package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CCity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CCity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CCityRepository extends JpaRepository<CCity, Long>, JpaSpecificationExecutor<CCity> {
}
