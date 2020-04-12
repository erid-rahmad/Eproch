package com.bhp.opusb.repository;

import com.bhp.opusb.domain.DocumentTypeBusinessCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DocumentTypeBusinessCategory entity.
 */
@Repository
public interface DocumentTypeBusinessCategoryRepository extends JpaRepository<DocumentTypeBusinessCategory, Long>, JpaSpecificationExecutor<DocumentTypeBusinessCategory> {
    void deleteByDocumentType_Id(Long id);
}
