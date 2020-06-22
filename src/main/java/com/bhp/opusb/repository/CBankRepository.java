package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CBank;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CBank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CBankRepository extends JpaRepository<CBank, Long>, JpaSpecificationExecutor<CBank> {
}
