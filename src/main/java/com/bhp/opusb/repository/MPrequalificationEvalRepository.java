package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPrequalificationEval;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalificationEval entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalificationEvalRepository extends JpaRepository<MPrequalificationEval, Long>, JpaSpecificationExecutor<MPrequalificationEval> {
}
