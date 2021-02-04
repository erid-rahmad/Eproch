package com.bhp.opusb.repository;

import com.bhp.opusb.domain.PaDashboardItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaDashboardItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaDashboardItemRepository extends JpaRepository<PaDashboardItem, Long>, JpaSpecificationExecutor<PaDashboardItem> {
}
