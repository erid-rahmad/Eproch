package com.bhp.opusb.repository;

import com.bhp.opusb.domain.DocumentTypeBusinessCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DocumentTypeBusinessCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentTypeBusinessCategoryRepository extends JpaRepository<DocumentTypeBusinessCategory, Long> {
}
