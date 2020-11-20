package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.CUnitOfMeasure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CUnitOfMeasure entity.
 */
@Repository
public interface CUnitOfMeasureRepository extends JpaRepository<CUnitOfMeasure, Long>, JpaSpecificationExecutor<CUnitOfMeasure> {

  Optional<CUnitOfMeasure> findFirstByCodeAndAdOrganizationId(String code, Long orgId);
}
