package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdForm;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdFormRepository extends JpaRepository<AdForm, Long>, JpaSpecificationExecutor<AdForm> {
}
