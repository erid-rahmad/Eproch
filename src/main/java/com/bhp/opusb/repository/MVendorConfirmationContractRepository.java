package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVendorConfirmationContract;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVendorConfirmationContract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorConfirmationContractRepository extends JpaRepository<MVendorConfirmationContract, Long>, JpaSpecificationExecutor<MVendorConfirmationContract> {
}
