package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ADColumn;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADColumn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ADColumnRepository extends JpaRepository<ADColumn, Long>, JpaSpecificationExecutor<ADColumn> {
}
