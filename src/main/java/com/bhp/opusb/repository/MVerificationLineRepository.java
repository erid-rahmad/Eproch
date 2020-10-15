package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVerificationLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVerificationLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVerificationLineRepository extends JpaRepository<MVerificationLine, Long>, JpaSpecificationExecutor<MVerificationLine> {
}
