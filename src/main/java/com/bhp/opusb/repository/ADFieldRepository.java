package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ADField;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADField entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ADFieldRepository extends JpaRepository<ADField, Long>, JpaSpecificationExecutor<ADField> {
}
