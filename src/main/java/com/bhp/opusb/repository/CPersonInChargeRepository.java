package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CPersonInCharge;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CPersonInCharge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPersonInChargeRepository extends JpaRepository<CPersonInCharge, Long>, JpaSpecificationExecutor<CPersonInCharge> {
}
