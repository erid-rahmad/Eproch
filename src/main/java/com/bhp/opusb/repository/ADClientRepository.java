package com.bhp.opusb.repository;

import com.bhp.opusb.domain.ADClient;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ADClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ADClientRepository extends JpaRepository<ADClient, Long>, JpaSpecificationExecutor<ADClient> {
}
