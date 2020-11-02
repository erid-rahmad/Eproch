package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CPicBusinessCat;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CPicBusinessCat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CPicBusinessCatRepository extends JpaRepository<CPicBusinessCat, Long>, JpaSpecificationExecutor<CPicBusinessCat> {
}
