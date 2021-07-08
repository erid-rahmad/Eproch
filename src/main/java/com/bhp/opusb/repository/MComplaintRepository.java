package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MComplaint;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MComplaint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MComplaintRepository extends GenericDocumentRepository<MComplaint, Long>, JpaSpecificationExecutor<MComplaint> {
}
