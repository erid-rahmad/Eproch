package com.bhp.opusb.repository;

import com.bhp.opusb.domain.PersonInCharge;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PersonInCharge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonInChargeRepository extends JpaRepository<PersonInCharge, Long>, JpaSpecificationExecutor<PersonInCharge> {
}
