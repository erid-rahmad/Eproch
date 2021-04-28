package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPrequalificationDateSet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalificationDateSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalificationDateSetRepository extends JpaRepository<MPrequalificationDateSet, Long>, JpaSpecificationExecutor<MPrequalificationDateSet> {
}
