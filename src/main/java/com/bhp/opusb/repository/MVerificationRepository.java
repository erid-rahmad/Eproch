package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVerification;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVerification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVerificationRepository extends JpaRepository<MVerification, Long>, JpaSpecificationExecutor<MVerification> {
}
