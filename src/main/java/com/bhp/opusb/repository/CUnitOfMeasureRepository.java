package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CUnitOfMeasure;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CUnitOfMeasure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CUnitOfMeasureRepository extends JpaRepository<CUnitOfMeasure, Long>, JpaSpecificationExecutor<CUnitOfMeasure> {
}
