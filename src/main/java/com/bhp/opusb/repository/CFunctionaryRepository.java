package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CFunctionary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CFunctionary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CFunctionaryRepository extends JpaRepository<CFunctionary, Long>, JpaSpecificationExecutor<CFunctionary> {
}
