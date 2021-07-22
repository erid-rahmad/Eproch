package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPrequalificationEvalFile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalificationEvalFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalificationEvalFileRepository extends JpaRepository<MPrequalificationEvalFile, Long>, JpaSpecificationExecutor<MPrequalificationEvalFile> {
}
