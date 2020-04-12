package com.bhp.opusb.repository;

import java.util.List;
import java.util.Set;

import com.bhp.opusb.domain.DocumentType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DocumentType entity.
 */
@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long>, JpaSpecificationExecutor<DocumentType> {

    @Query(
        "SELECT DISTINCT new com.bhp.opusb.domain.DocumentType(d.id, d.name)"
        + " FROM DocumentType d"
        + " JOIN d.documentTypeBusinessCategories db "
        + " JOIN db.businessCategory b"
        + " WHERE db.mandatory = true"
        + " AND b.id IN :ids")
    List<DocumentType> findMandatoryByBusinessCategories(@Param("ids") Set<Long> ids);

    @Query(
        "SELECT DISTINCT new com.bhp.opusb.domain.DocumentType(d.id, d.name)"
        + " FROM DocumentType d"
        + " JOIN d.documentTypeBusinessCategories db "
        + " JOIN db.businessCategory b"
        + " WHERE db.additional = true"
        + " AND b.id IN :ids")
    List<DocumentType> findAdditionalByBusinessCategories(@Param("ids") Set<Long> ids);
}
