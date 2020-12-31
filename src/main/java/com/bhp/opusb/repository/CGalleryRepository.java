package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CGallery;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CGallery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CGalleryRepository extends JpaRepository<CGallery, Long>, JpaSpecificationExecutor<CGallery> {
}
