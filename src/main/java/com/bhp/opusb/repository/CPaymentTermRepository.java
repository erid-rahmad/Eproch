package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CPaymentTerm;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CPaymentTerm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPaymentTermRepository extends JpaRepository<CPaymentTerm, Long>, JpaSpecificationExecutor<CPaymentTerm> {
}
