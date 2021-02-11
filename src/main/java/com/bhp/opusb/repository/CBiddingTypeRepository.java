package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CBiddingType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CBiddingType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CBiddingTypeRepository extends JpaRepository<CBiddingType, Long>, JpaSpecificationExecutor<CBiddingType> {
}
