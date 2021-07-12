package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MBlacklist;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MBlacklist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MBlacklistRepository extends GenericDocumentRepository<MBlacklist, Long>, JpaSpecificationExecutor<MBlacklist> {
}
