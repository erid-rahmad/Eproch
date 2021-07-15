package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CPrequalificationMethod;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CPrequalificationMethod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPrequalificationMethodRepository extends JpaRepository<CPrequalificationMethod, Long>, JpaSpecificationExecutor<CPrequalificationMethod> {
}
