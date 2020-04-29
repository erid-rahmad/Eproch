package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ADTable;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ADTableRepository extends JpaRepository<ADTable, Long>, JpaSpecificationExecutor<ADTable> {
}
