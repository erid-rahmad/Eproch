package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CTaxRate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CTaxRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CTaxRateRepository extends JpaRepository<CTaxRate, Long>, JpaSpecificationExecutor<CTaxRate> {
}
