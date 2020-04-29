package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ADReferenceList;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADReferenceList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ADReferenceListRepository extends JpaRepository<ADReferenceList, Long>, JpaSpecificationExecutor<ADReferenceList> {
}
