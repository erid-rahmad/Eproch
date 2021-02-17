package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.CVendor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CVendor entity.
 */
@Repository
public interface CVendorRepository extends JpaRepository<CVendor, Long>, JpaSpecificationExecutor<CVendor> {

  Optional<CVendor> findFirstByCode(String code);

  @Modifying
  @Query("UPDATE CVendor v SET v.documentAction = :action, v.documentStatus = :status, v.approved = true, v.processed = true WHERE v.id = :id")
  void updateDocumentStatus(@Param("id") long id, @Param("action") String action, @Param("status") String status);

  Optional<CVendor> findFirstByName(String name);

}
