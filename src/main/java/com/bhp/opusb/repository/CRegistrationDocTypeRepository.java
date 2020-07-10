package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CRegistrationDocType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CRegistrationDocType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CRegistrationDocTypeRepository extends JpaRepository<CRegistrationDocType, Long>, JpaSpecificationExecutor<CRegistrationDocType> {
}
