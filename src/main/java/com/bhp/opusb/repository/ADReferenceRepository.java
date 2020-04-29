package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ADReference;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADReference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ADReferenceRepository extends JpaRepository<ADReference, Long>, JpaSpecificationExecutor<ADReference> {
}
