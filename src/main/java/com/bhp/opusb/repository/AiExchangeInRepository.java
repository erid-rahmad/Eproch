package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.AiExchangeIn;
import com.bhp.opusb.domain.enumeration.AiStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AiExchangeIn entity.
 */
@Repository
public interface AiExchangeInRepository extends JpaRepository<AiExchangeIn, Long>, JpaSpecificationExecutor<AiExchangeIn> {

  Optional<AiExchangeIn> findFirstByEntityTypeAndEntityIdAndStatus(String type, Long id, AiStatus status);
}
