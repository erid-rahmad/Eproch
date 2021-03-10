package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MVendorSuggestion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the MVendorSuggestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MVendorSuggestionRepository extends JpaRepository<MVendorSuggestion, Long>, JpaSpecificationExecutor<MVendorSuggestion> {
    @Query(value = "SELECT a FROM MVendorSuggestion a WHERE a.bidding.id=?1" )
    List<MVendorSuggestion> findbyheaderid(long a);

}
