package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CConvertionRate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CConvertionRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CConvertionRateRepository extends JpaRepository<CConvertionRate, Long>, JpaSpecificationExecutor<CConvertionRate> {
}
