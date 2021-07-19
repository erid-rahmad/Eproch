package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPrequalRegistration;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalRegistration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalRegistrationRepository extends JpaRepository<MPrequalRegistration, Long>, JpaSpecificationExecutor<MPrequalRegistration> {
}
