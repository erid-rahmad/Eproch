package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MContractLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MContractLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MContractLineRepository extends JpaRepository<MContractLine, Long>, JpaSpecificationExecutor<MContractLine> {
}
