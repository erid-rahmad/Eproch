package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AiExchangeOut;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AiExchangeOut entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AiExchangeOutRepository extends JpaRepository<AiExchangeOut, Long>, JpaSpecificationExecutor<AiExchangeOut> {
}
