package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.CDocumentType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CDocumentType entity.
 */
@Repository
public interface CDocumentTypeRepository extends JpaRepository<CDocumentType, Long>, JpaSpecificationExecutor<CDocumentType> {

  Optional<CDocumentType> findFirstByName(String name);
}
