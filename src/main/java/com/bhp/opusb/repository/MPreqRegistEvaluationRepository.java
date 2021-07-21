package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.MPreqRegistEvaluation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPreqRegistEvaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPreqRegistEvaluationRepository extends JpaRepository<MPreqRegistEvaluation, Long>, JpaSpecificationExecutor<MPreqRegistEvaluation> {
    @Query("select mpre from MPreqRegistEvaluation mpre where mpre.vendor.id = ?1 and mpre.prequalification.id = ?2")
    Optional<MPreqRegistEvaluation> findExistingId(Long vendorId, Long prequalificationId);
}
