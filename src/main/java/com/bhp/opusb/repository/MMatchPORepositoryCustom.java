package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MMatchPO;
import com.bhp.opusb.service.dto.MMatchPOCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MMatchPORepositoryCustom {

  /**
   * Find all match po records that match the following criteria:
   * - Not existed in m_verification_line table.
   * - Existed in m_verification_line table, but has been canceled or rejected.
   * - Has matchType = 1.
   */
  Page<MMatchPO> findNonInvoicedMatchPOs(MMatchPOCriteria criteria, Pageable page);
}
