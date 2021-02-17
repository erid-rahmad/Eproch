package com.bhp.opusb.repository;

import java.math.BigDecimal;
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
    "AND m.orderSuffix = ?8"
  )
  Optional<MMatchPO> findByKeys(String matchType, String orgCode, String docType, String poNo, String receiptNo,
      Integer lineNoPo, Integer lineNoMr, String orderSuffix);

  /**
   * Find the respective line that matches the incoming reversed receipt line (matchType = 4).
   * The line is uniquely identified by its orgCode, docType, poNo, lineNoPo, lineNoMr, and orderSuffix.
   * The incoming reversed line to match has higher receiptNo.
   */
  @Query("SELECT m FROM MMatchPO m " +
    "WHERE m.mMatchType = '1' " +
    "AND m.adOrganization.code = ?1 " +
    "AND m.cDocType = ?2 " +
    "AND m.poNo = ?3 " +
    "AND TO_NUMBER(m.receiptNo, '999999999') < ?4 " +
    "AND m.lineNoPo = ?5 " +
    "AND m.lineNoMr = ?6 " +
    "AND m.orderSuffix = ?7"
  )
  Optional<MMatchPO> findReversedLine(String orgCode, String docType, String poNo, BigDecimal receiptNo, Integer lineNoPo,
      Integer lineNoMr, String orderSuffix);
}
