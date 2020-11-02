package com.bhp.opusb.repository;

import com.bhp.opusb.domain.CAttachment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CAttachmentRepository extends JpaRepository<CAttachment, Long>, JpaSpecificationExecutor<CAttachment> {
}
