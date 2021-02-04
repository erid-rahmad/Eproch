package com.bhp.opusb.repository;

import com.bhp.opusb.domain.PaDashboardPreference;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaDashboardPreference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaDashboardPreferenceRepository extends JpaRepository<PaDashboardPreference, Long>, JpaSpecificationExecutor<PaDashboardPreference> {
}
