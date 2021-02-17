package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVendorSuggestion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVendorSuggestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorSuggestionRepository extends JpaRepository<MVendorSuggestion, Long>, JpaSpecificationExecutor<MVendorSuggestion> {
}
