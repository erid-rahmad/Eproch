package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ReferenceList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReferenceList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferenceListRepository extends JpaRepository<ReferenceList, Long>, JpaSpecificationExecutor<ReferenceList> {
}
