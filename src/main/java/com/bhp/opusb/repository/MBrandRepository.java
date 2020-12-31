package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBrand;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBrand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBrandRepository extends JpaRepository<MBrand, Long>, JpaSpecificationExecutor<MBrand> {
}
