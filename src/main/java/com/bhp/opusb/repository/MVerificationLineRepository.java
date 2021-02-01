package com.bhp.opusb.repository;

import java.math.BigDecimal;
import java.util.Optional;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MVerificationLine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MVerificationLine entity.
 */
@Repository
public interface MVerificationLineRepository extends JpaRepository<MVerificationLine, Long>, JpaSpecificationExecutor<MVerificationLine> {

  /**
   * Find the respective line that matches the incoming reversed receipt line (matchType = 4).
   * The line is uniquely identified by its orgCode, docType, poNo, lineNoPo, lineNoMr, and orderSuffix.
   * The incoming reversed line to match has higher receiptNo.
   */
  @Query("SELECT vl " +
    "FROM MVerificationLine vl " +
    "WHERE vl.adOrganization = ?1 " +
    "AND vl.cDocType = ?2 " +
    "AND vl.poNo = ?3 " +
    "AND TO_NUMBER(vl.receiveNo, '999999999') < ?4 " +
    "AND vl.lineNoPo = ?5 " +
    "AND vl.lineNoMr = ?6 " +
    "AND vl.orderSuffix = ?7 " +
    "AND vl.verification.documentStatus IN ('DRF', 'SMT', 'ROP')"
  )
  Optional<MVerificationLine> getFirstReversedReceiptLine(ADOrganization org, String docType, String poNo,
      BigDecimal receiptNo, Integer lineNoPo, Integer lineNoMr, String orderSuffix);

  /**
   * Find the respective line that matches the incoming reversed AP invoice line (matchType = 3).
   * The line is uniquely identified by its orgCode, docType, poNo, lineNoPo, lineNoMr, and orderSuffix.
   * The incoming reversed line to match has higher receiptNo.
   */
  @Query("SELECT vl " +
    "FROM MVerificationLine vl " +
    "WHERE vl.adOrganization = ?1 " +
    "AND vl.cDocType = ?2 " +
    "AND vl.poNo = ?3 " +
    "AND TO_NUMBER(vl.receiveNo, '999999999') < ?4 " +
    "AND vl.lineNoPo = ?5 " +
    "AND vl.lineNoMr = ?6 " +
    "AND vl.orderSuffix = ?7 " +
    "AND vl.verification.documentStatus = 'APV'"
  )
  Optional<MVerificationLine> getFirstReversedAPInvoiceLine(ADOrganization org, String docType, String poNo,
      BigDecimal receiptNo, Integer lineNoPo, Integer lineNoMr, String orderSuffix);

}
