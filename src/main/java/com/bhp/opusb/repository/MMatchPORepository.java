package com.bhp.opusb.repository;

import java.util.Optional;

import com.bhp.opusb.domain.MMatchPO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MMatchPO entity.
 */
@Repository
public interface MMatchPORepository extends JpaRepository<MMatchPO, Long>, JpaSpecificationExecutor<MMatchPO>, MMatchPORepositoryCustom {

  @Query("SELECT m FROM MMatchPO m " +
    "WHERE m.mMatchType = ?1 " +
    "AND m.adOrganization.code = ?2 " +
    "AND m.cDocType = ?3 " +
    "AND m.poNo = ?4 " +
    "AND m.receiptNo = ?5 " +
    "AND m.lineNoPo = ?6 " +
    "AND m.lineNoMr = ?7 " +
    "AND m.orderSuffix = ?8")
  Optional<MMatchPO> findByKeys(String matchType, String orgId, String docType, String poNo, String receiptNo,
      Integer lineNoPo, Integer lineNoMr, String orderSuffix);
}
