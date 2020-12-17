package com.bhp.opusb.repository;

import com.bhp.opusb.domain.MMatchPO;
import com.bhp.opusb.service.dto.MMatchPOCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MMatchPORepositoryCustom {
  Page<MMatchPO> findNonInvoicedMatchPOs(MMatchPOCriteria criteria, Pageable page);
}
