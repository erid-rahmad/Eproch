package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MRfqLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MRfqLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MRfqLineRepository extends JpaRepository<MRfqLine, Long>, JpaSpecificationExecutor<MRfqLine> {
}
