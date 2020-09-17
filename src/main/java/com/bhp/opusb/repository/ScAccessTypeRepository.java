package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ScAccessType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ScAccessType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScAccessTypeRepository extends JpaRepository<ScAccessType, Long>, JpaSpecificationExecutor<ScAccessType> {
}
