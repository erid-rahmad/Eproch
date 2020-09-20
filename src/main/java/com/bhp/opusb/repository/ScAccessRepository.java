package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ScAccess;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ScAccess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScAccessRepository extends JpaRepository<ScAccess, Long>, JpaSpecificationExecutor<ScAccess> {
}
