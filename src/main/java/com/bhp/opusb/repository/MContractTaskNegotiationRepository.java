package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MContractTaskNegotiation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MContractTaskNegotiation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MContractTaskNegotiationRepository extends JpaRepository<MContractTaskNegotiation, Long>, JpaSpecificationExecutor<MContractTaskNegotiation> {
}
