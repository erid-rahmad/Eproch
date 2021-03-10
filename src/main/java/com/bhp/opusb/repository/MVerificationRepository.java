package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.MVerification;

import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVerification entity.
 */
@Repository
public interface MVerificationRepository extends GenericDocumentRepository<MVerification, Long> {

  Optional<MVerification> findFirstByDocumentNo(String documentNo);

}
