package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ADTab;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADTab entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ADTabRepository extends JpaRepository<ADTab, Long>, JpaSpecificationExecutor<ADTab> {
}
