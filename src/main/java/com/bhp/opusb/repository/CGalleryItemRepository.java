package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CGalleryItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CGalleryItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CGalleryItemRepository extends JpaRepository<CGalleryItem, Long>, JpaSpecificationExecutor<CGalleryItem> {
}
