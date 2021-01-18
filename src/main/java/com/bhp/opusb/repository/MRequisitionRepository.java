package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MRequisition;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MRequisition entity.
 */
@Repository
public interface MRequisitionRepository extends JpaRepository<MRequisition, Long>, JpaSpecificationExecutor<MRequisition> {

    @Modifying
    @Query("UPDATE MRequisition v SET v.documentAction = :action, v.documentStatus = :status, v.approved = true, v.processed = true WHERE v.id = :id")
    void updateDocumentStatus(@Param("id") long id, @Param("action") String action, @Param("status") String status);
}
