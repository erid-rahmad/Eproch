package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CCurrency;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CCurrency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CCurrencyRepository extends JpaRepository<CCurrency, Long>, JpaSpecificationExecutor<CCurrency> {
}
