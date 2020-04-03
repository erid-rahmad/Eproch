package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CompanyFunctionary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompanyFunctionary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyFunctionaryRepository extends JpaRepository<CompanyFunctionary, Long>, JpaSpecificationExecutor<CompanyFunctionary> {
}
