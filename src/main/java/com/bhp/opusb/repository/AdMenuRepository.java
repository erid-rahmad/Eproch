package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdMenu;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdMenuRepository extends JpaRepository<AdMenu, Long>, JpaSpecificationExecutor<AdMenu> {
}
