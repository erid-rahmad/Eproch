package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MMatchPO;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MMatchPO entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MMatchPORepository extends JpaRepository<MMatchPO, Long>, JpaSpecificationExecutor<MMatchPO> {
}
