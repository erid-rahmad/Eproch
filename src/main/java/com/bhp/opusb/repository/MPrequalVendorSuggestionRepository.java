package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MPrequalVendorSuggestion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MPrequalVendorSuggestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MPrequalVendorSuggestionRepository extends JpaRepository<MPrequalVendorSuggestion, Long>, JpaSpecificationExecutor<MPrequalVendorSuggestion> {
}
