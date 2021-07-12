package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBlacklistLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBlacklistLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBlacklistLineRepository extends JpaRepository<MBlacklistLine, Long>, JpaSpecificationExecutor<MBlacklistLine> {
}
