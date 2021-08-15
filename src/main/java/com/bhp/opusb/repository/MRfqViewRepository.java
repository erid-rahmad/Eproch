package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MRfqView;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MRfqView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MRfqViewRepository extends JpaRepository<MRfqView, Long>, JpaSpecificationExecutor<MRfqView> {
}
