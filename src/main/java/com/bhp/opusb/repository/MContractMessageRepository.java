package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MContractMessage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MContractMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MContractMessageRepository extends JpaRepository<MContractMessage, Long>, JpaSpecificationExecutor<MContractMessage> {
}
