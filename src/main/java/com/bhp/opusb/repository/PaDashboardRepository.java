package com.bhp.opusb.repository;

import com.bhp.opusb.domain.PaDashboard;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaDashboard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaDashboardRepository extends JpaRepository<PaDashboard, Long>, JpaSpecificationExecutor<PaDashboard> {
}
