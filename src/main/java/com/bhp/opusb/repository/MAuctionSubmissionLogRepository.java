package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MAuctionSubmissionLog;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MAuctionSubmissionLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MAuctionSubmissionLogRepository extends JpaRepository<MAuctionSubmissionLog, Long>, JpaSpecificationExecutor<MAuctionSubmissionLog> {
}
