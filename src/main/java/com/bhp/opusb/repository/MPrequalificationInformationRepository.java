package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPrequalificationInformation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalificationInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalificationInformationRepository extends GenericDocumentRepository<MPrequalificationInformation, Long>, JpaSpecificationExecutor<MPrequalificationInformation> {
}
