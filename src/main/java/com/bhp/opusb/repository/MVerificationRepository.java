package com.bhp.opusb.repository;

import java.time.LocalDate;

import com.bhp.opusb.domain.MVerification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVerification entity.
 */
@Repository
public interface MVerificationRepository extends JpaRepository<MVerification, Long>, JpaSpecificationExecutor<MVerification> {

  int countByVerificationDateBetween(LocalDate startDate, LocalDate endDate);
}
