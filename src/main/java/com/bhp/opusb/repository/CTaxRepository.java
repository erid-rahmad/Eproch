package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CTax;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CTax entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CTaxRepository extends JpaRepository<CTax, Long>, JpaSpecificationExecutor<CTax> {
}
