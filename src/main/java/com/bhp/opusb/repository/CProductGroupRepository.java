package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CProductGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CProductGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CProductGroupRepository extends JpaRepository<CProductGroup, Long>, JpaSpecificationExecutor<CProductGroup> {
}
