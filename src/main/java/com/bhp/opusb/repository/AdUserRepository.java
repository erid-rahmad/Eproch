package com.bhp.opusb.repository;

import com.bhp.opusb.domain.AdUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdUserRepository extends JpaRepository<AdUser, Long>, JpaSpecificationExecutor<AdUser> {
}
