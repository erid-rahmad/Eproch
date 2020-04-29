package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ADFieldGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADFieldGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ADFieldGroupRepository extends JpaRepository<ADFieldGroup, Long>, JpaSpecificationExecutor<ADFieldGroup> {
}
