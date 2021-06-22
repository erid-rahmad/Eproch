package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MContract;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MContract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MContractRepository extends GenericDocumentRepository<MContract, Long> {
}
