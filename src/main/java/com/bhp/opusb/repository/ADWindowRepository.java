package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ADWindow;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADWindow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ADWindowRepository extends JpaRepository<ADWindow, Long>, JpaSpecificationExecutor<ADWindow> {
}
